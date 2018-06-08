import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import magma.magma;
public class Test {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        byte[] text_byte = null, key_byte = null;
        magma a = new magma();
        byte[] IV = new byte[8];
        byte[] msg = null;
        Scanner in = new Scanner(System.in);
        FileInputStream fis_text, fis_key;
        FileOutputStream fos_text = null;
        File name_file = new File("/Users/your_dream/Desktop/text.txt");
        File key_file = new File("/Users/your_dream/Desktop/key.txt");
        File coder_file = new File("/Users/your_dream/Desktop/coder.txt");
        File origin_file = new File("/Users/your_dream/Desktop/orig_text.txt");

        System.out.println("Введите 1 - для шифрования");
        System.out.println("Введите 2 - для расшифрования");
        int f = in.nextInt();

        switch(f){
            case 1:
                fis_text = new FileInputStream(name_file);
                fis_key = new FileInputStream(key_file);
                fos_text = new FileOutputStream(coder_file);
                text_byte = new byte[(int) name_file.length()];
                key_byte = new byte[(int) key_file.length()];
                fis_text.read(text_byte);
                fis_key.read(key_byte);
                fis_text.close();
                fis_key.close();
                a.setKey(key_byte);
                msg = a.padding(text_byte, ' ');
                IV = a.genIV();
                a.encrypt(msg, IV);

                if(coder_file.exists() == false)  //создаем файл если его нет
                    try {
                        coder_file.createNewFile();
                        fos_text = new FileOutputStream(coder_file);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                //Запись в файл
                try {
                    fos_text.write(IV,0,IV.length);
                    fos_text.write(msg, 0, msg.length);
                    fos_text.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                break;

            case 2:
                try {
                    fis_text = new FileInputStream(coder_file);
                    fis_key = new FileInputStream(key_file);
                    fos_text = new FileOutputStream(origin_file);
                    text_byte = new byte[(int) coder_file.length()];
                    key_byte = new byte[(int) key_file.length()];
                    fis_text.read(text_byte);
                    fis_key.read(key_byte);
                } catch (FileNotFoundException ex){
                    System.out.print(ex);
                } catch (IOException ex){
                    System.out.print(ex);
                }

                a.setKey(key_byte);

                msg = new byte[text_byte.length-IV.length];
                System.arraycopy(text_byte, 0, IV, 0, 8);
                System.arraycopy(text_byte, IV.length, msg, 0, text_byte.length-IV.length);

                a.encrypt(msg, IV);

                if(origin_file.exists() == false)  //создаем файл если его нет
                    try {
                        origin_file.createNewFile();
                        fos_text = new FileOutputStream(origin_file);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                //Запись в файл
                try {
                    fos_text.write(msg, 0, msg.length);
                    fos_text.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
        }
    }
}






