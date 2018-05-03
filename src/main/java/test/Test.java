package test;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Test {
   public static void main(String [] args) throws Exception {
      new Test().test();
   }

   private void test () throws Exception {
      byte [] bytes;
      bytes=serialize(Hi.class);
      System.out.println(Hexdump.toHexdump(bytes).trim());
      Hi hi=deserialize(bytes);
      hi.hi();

      bytes=serialize(Hi2.class);
      System.out.println(Hexdump.toHexdump(bytes).trim());
      Hi2 hi2=deserialize(bytes);
      hi2.hi();
   }

   @SuppressWarnings("unchecked")
   private <T> T deserialize(byte [] serializedObject) throws IOException, ClassNotFoundException {
      ObjectInputStream is=new ObjectInputStream(new ByteArrayInputStream(serializedObject));
      return (T) is.readObject();
   }

   // https://docs.oracle.com/javase/10/docs/specs/serialization/protocol.html#overview
   // https://www.javaworld.com/article/2072752/the-java-serialization-algorithm-revealed.html
   private byte []  serialize(Class<? extends Serializable> clazz) throws IOException {
      long serialVersionID=ObjectStreamClass.lookup(clazz).getSerialVersionUID();
      ByteArrayOutputStream baos=new ByteArrayOutputStream();
      DataOutputStream os=new DataOutputStream(baos);
      String name=clazz.getName();
      os.writeLong(0xACED000573720000L | name.length());
      os.write(name.getBytes(StandardCharsets.UTF_8));
      os.writeLong(serialVersionID);
      os.writeByte(0x02);
      os.writeInt(0x00007870);
      os.flush();
      return baos.toByteArray();
   }

   protected final static class Hi implements Serializable {
      private Hi() {
         throw new RuntimeException("Cannot do this");
      }

      public void hi() {
         System.out.println("Hi");
      }

      private long x= 12341313156L;
      private int y = 1122;
      private String z ="sgdfgsdf";
   }
}