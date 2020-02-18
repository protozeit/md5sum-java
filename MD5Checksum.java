import java.io.*;
import java.security.MessageDigest;

public class MD5Checksum {

   public static byte[] createChecksum(String filename) throws Exception {
       InputStream fis =  new FileInputStream(filename);

       byte[] buffer = new byte[1024];
       MessageDigest complete = MessageDigest.getInstance("MD5");
       int numRead;

       do {
           numRead = fis.read(buffer);
           if (numRead > 0) {
               complete.update(buffer, 0, numRead);
           }
       } while (numRead != -1);

       fis.close();
       return complete.digest();
   }

   // see this How-to for a faster way to convert
   // a byte array to a HEX string
   public static String getMD5Checksum(String filename) throws Exception {
       byte[] b = createChecksum(filename);
       String result = "";

       for (int i=0; i < b.length; i++) {
           result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
       }
       return result;
   }

   public static void main(String args[]) {

       if (args.length == 0) {
           System.out.println("usage: java MD5Checksum <filename>");
       }
       try {
           for (String file : args) {
               System.out.println(file +" - "+ getMD5Checksum(file));
           }
           if (args.length == 2) {
               if (getMD5Checksum(args[0]).equals(getMD5Checksum(args[1]))){
                 System.out.println("The 2 files are identical");
               } else {
                 System.out.println("The 2 files are not identical");
               }
           }
       }
       catch (Exception e) {
           e.printStackTrace();
       }
   }
}
