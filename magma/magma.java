

import java.nio.ByteBuffer;

// Основной класс шифрования
final public class magma {
    private final static byte table[][] = {
            {4, 10, 9, 2, 13, 8, 0, 14, 6, 11, 1, 12, 7, 15, 5, 3},
            {14, 11, 4, 12, 6, 13, 15, 10, 2, 3, 8, 1, 0, 7, 5, 9},
            {5, 8, 1, 13, 10, 3, 4, 2, 14, 15, 12, 7, 6, 0, 9, 11},
            {7, 13, 10, 1, 0, 8, 9, 15, 14, 4, 6, 12, 11, 2, 5, 3},
            {6, 12, 7, 1, 5, 15, 13, 8, 4, 10, 9, 14, 0, 3, 11, 2},
            {4, 11, 10, 0, 7, 2, 1, 13, 3, 6, 8, 5, 9, 12, 15, 14},
            {13, 11, 4, 1, 3, 15, 5, 9, 0, 10, 14, 7, 6, 8, 2, 12},
            {1, 15, 13, 0, 5, 7, 10, 4, 9, 2, 3, 14, 6, 11, 8, 12}
    };

    private byte key[][] = new byte[8][4];

    public void setKey(byte[] key) { //наш Ключ
        int k = 0;
        for(int i = 0;i<8;i++)
            for(int j = 0;j<4;j++){
                this.key[i][j] = key[k];
                k++;
            }
    }
    //таблица порядка взятия чисел из таблица замен
    private final static int[] keyMap = {0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0};

    //вектор инициализации
    public byte[] genIV() {
        byte[] c = new byte[8];
        for(int i=0;i<8;i++) c[i] = (byte)((int)(Math.random()*10));
        return c;
    }

    public void encrypt(byte[] data, byte[] IV){
        int block = data.length / 8;
        byte[][] O = new byte[block+1][IV.length];
        byte[][] data_tmp = new byte[block][IV.length];
        int r;
        System.arraycopy(IV, 0, O[0], 0, IV.length);
        for(int i=0;i<block;i++){
            encipher(O[i]);
            O[i+1] = O[i];
            System.arraycopy(data, i*8, data_tmp[i], 0, 8);
            for(int j=0;j<8;j++) data_tmp[i][j]^=O[i][j];
            System.arraycopy(data_tmp[i], 0, data, i*8, 8);
        }
    }
    public void decrypt(byte[] data, byte[] IV){
        int block = data.length / 8;
        byte[][] O = new byte[block+1][IV.length];
        byte[][] data_tmp = new byte[block][IV.length];
        int r;
        System.arraycopy(IV, 0, O[0], 0, IV.length);
        for(int i=0;i<block;i++){
            decipher(O[i]);
            O[i+1] = O[i];
            System.arraycopy(data, i*8, data_tmp[i], 0, 8);
            for(int j=0;j<8;j++) data_tmp[i][j]^=O[i][j];
            System.arraycopy(data_tmp[i], 0, data, i*8, 8);
        }
    }

    public void encipher(byte[] data) {

        byte[] A = new byte[4], B = new byte[4];

        System.arraycopy(data, 0, A, 0, 4);
        System.arraycopy(data, 4, B, 0, 4);

        for (int k = 0; k < 32; k++) {
            byte[] K = key[keyMap[k]];
            int buf = ByteBuffer.wrap(A).getInt() + ByteBuffer.wrap(K).getInt();
            buf &= 0xffffffff; // A + K (mod 2^32)
            int[] s = {
                    (buf & 0xF0000000) >>> 28,
                    (buf & 0x0F000000) >>> 24,
                    (buf & 0x00F00000) >>> 20,
                    (buf & 0x000F0000) >>> 16,
                    (buf & 0x0000F000) >>> 12,
                    (buf & 0x00000F00) >>> 8,
                    (buf & 0x000000F0) >>> 4,
                    (buf & 0x0000000F)
            };
            buf = 0x00000000;
            for (int b = 0; b < 8; b++) {
                buf <<= 4;
                buf += table[b][s[b] & 0x0000000f];
            }
            buf = ((buf << 11) | (buf >>> 21));
            byte[] resBytes = ByteBuffer.allocate(4).putInt(buf).array();
            byte[] newB = {0x00, 0x00, 0x00, 0x00};

            System.arraycopy(A, 0, newB, 0, 4);
            for (int b = 0; b < 4; b++) {
                A[b] = (byte) (resBytes[b] ^ B[b]);
            }
            System.arraycopy(newB, 0, B, 0, 4);
        }
        System.arraycopy(A, 0, data, 0, 4);
        System.arraycopy(B, 0, data, 4, 4);
    }

    public void decipher(byte[] data) {

        byte[] A = new byte[4], B = new byte[4];

        System.arraycopy(data, 0, A, 0, 4);
        System.arraycopy(data, 4, B, 0, 4);

        for (int k = 0; k < 32; k++) {
            byte[] K = key[keyMap[31 - k]];
            int buf = ByteBuffer.wrap(A).getInt() + ByteBuffer.wrap(K).getInt();
            buf &= 0xffffffff; // A + K (mod 2^32)
            int[] s = {
                    (buf & 0xF0000000) >>> 28,
                    (buf & 0x0F000000) >>> 24,
                    (buf & 0x00F00000) >>> 20,
                    (buf & 0x000F0000) >>> 16,
                    (buf & 0x0000F000) >>> 12,
                    (buf & 0x00000F00) >>> 8,
                    (buf & 0x000000F0) >>> 4,
                    (buf & 0x0000000F)
            };
            buf = 0x00000000;
            for (int b = 0; b < 8; b++) {
                buf <<= 4;
                buf += table[b][s[b] & 0x0000000f];
            }
            buf = ((buf << 11) | (buf >>> 21));
            byte[] resBytes = ByteBuffer.allocate(4).putInt(buf).array();
            byte[] newB = {0x00, 0x00, 0x00, 0x00};

            System.arraycopy(A, 0, newB, 0, 4);
            for (int b = 0; b < 4; b++) {
                A[b] = (byte) (resBytes[b] ^ B[b]);
            }
            System.arraycopy(newB, 0, B, 0, 4);
        }
        System.arraycopy(A, 0, data, 0, 4);
        System.arraycopy(B, 0, data, 4, 4);
    }

    public byte[] padding(byte[] a, int p) {
        int l = (a.length | 7) + 1;
        byte[] b = new byte[l];
        for (int i = 0; i < b.length; i++) b[i] = (byte) p;
        System.arraycopy(a, 0, b, 0, a.length);
        return b;
    }

}

