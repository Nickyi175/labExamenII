package sera;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.Date;

/**
 *
 * @author tomea listo Crear una clase llamada PSNUsers que tiene un atributo de
 tipo RandomAccessFile y uno de tipo HashTable llamado users que sirve para
 llevar el control de los usuarios agregados, todas las búsquedas se hacen con
 este atributo, no se hacen búsquedas en el archivo de ningún tipo porque es
 más rápido hacerlo en memoria que en disco.


 no listo En el constructor se inicializa el atributo RAF para que este
 conectado con el archivo psnUsers. Se llama la función reloadHashTable. 5% La
 función reloadHashTable recarga la lista enlazada. Con todos los registros NO
 BORRADOS que están en el archivo. Cada registro en el archivo es un elemento
 en la lista. Se guarda el código del registro y la posición DESPUÉS de leer
 el código del mismo. Hacer esta función privada, ya que solo se llama en el
 constructor. 10% addUser(String username). Agrega un nuevo registro al
 archivo, al agregarlo también se le agrega un elemento a la HashTable con los
 datos del usuario nuevo. Los datos de un usuario (usar dicho orden como
 formato) son: Username que viene de parámetro y que sé válida que sea ÚNICO.
 Por default tiene el acumulador de puntos por trofeos en 0. Por default tiene
 el contador de trofeos en 0. Por default el registro está activado. Si un
 registro tiene el dato activado en false se CONSIDERA BORRADO. 10%
 deactivateUser(String username). Buscar un usuario, si se encuentra, se
 escribe en disco como no activo y además se borra su registro en la
 HashTable. 15% addTrophiTo(String username, String trophyGame, String
 trophyName, Trophy type). Busca un usuario con ese username, si se encuentra
 se le adiciona un trofeo. Los trofeos se guardan en un archivo llamado
 psnUsers. El formato que se guarda por trofeo es: Código del User que se ganó
 el trofeo. String – tipo del trofeo. Nombre del juego al que pertenece
 trofeo. Nombre del trofeo. Fecha cuando se lo gano. 15% También recordar,
 aumentar el contador de trofeos y acumulador de puntos del usuario.

 playerInfo(String username). Busca un usuario con ese username. Si se
 encuentra se imprime TODOS sus datos. Al final se imprimen los datos de los
 trofeos que ha ganado con el formato: FECHA – TIPO - JUEGO – DESCRIPCIÓN. 10%
 */
public class PSNUsers {

    RandomAccessFile raf, psnUsers;
    HashTable users;
    long tamaño = 0;

    public PSNUsers() throws FileNotFoundException {

        try {

            raf = new RandomAccessFile("usuario", "rw");
            psnUsers = new RandomAccessFile("userss", "rw");
            reloadHashTable();
        } catch (FileNotFoundException e) {
            try {
                File f = new File("usuario");
                f.createNewFile();

                f = new File("userss");
                f.createNewFile();
                raf = new RandomAccessFile("usuario", "rw");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reloadHashTable() {

        tamaño = 0;
        try {
            raf.seek(0);
            users = new HashTable();
            while (raf.getFilePointer() < raf.length()) {
                String username = raf.readUTF();
                int trophyPoints = raf.readInt();
                int trophiesCount = raf.readInt();
                boolean registered = raf.readBoolean();

                if (!registered) {
                    continue;
                }

                users.add(username, tamaño);
                tamaño++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean addUser(String username) {

        if (users.search(username) != -1) {
            return false;
        }

        try {
            raf.seek(raf.length());
            raf.writeUTF(username);
            raf.writeInt(0);
            raf.writeInt(0);
            raf.writeBoolean(true);

            users.add(username, tamaño);
            tamaño++;

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void desactivateUser(String username) {

//    if (users.search(username) == -1) {
//        System.Userinfo.println("[deactivate] not found");
//        return;
//    }
//
//    try {
//        RandomAccessFile newUsers = new RandomAccessFile("users", "rw");
//        raf.seek(0);
//
//        long srcPos = 0;
//        long destPos = 0;
//        boolean userFound = false;
//
//        while (raf.getFilePointer() < raf.length()) {
//            String currentUsername = raf.readUTF();
//            int trophyPoints = raf.readInt();
//            int trophyCount = raf.readInt();
//            boolean isActive = raf.readBoolean();
//
//            if (username.equals(currentUsername)) {
//                userFound = true;
//                isActive = false;
//            }
//
//            long entryLength = 4L * 2 + 1 + 4L * 2;
//
//            if (userFound) {
//                srcPos += entryLength;
//            } else {
//                newUsers.seek(destPos);
//                newUsers.writeUTF(currentUsername);
//                newUsers.writeInt(trophyPoints);
//                newUsers.writeInt(trophyCount);
//                newUsers.writeBoolean(isActive);
//                destPos += entryLength;
//            }
//        }
//
//        if (userFound) {
//            FileChannel sourceChannel = raf.getChannel();
//            FileChannel destinationChannel = newUsers.getChannel();
//
//            sourceChannel.position(srcPos);
//            destinationChannel.position(destPos);
//
//            long count = raf.length() - srcPos;
//
//            while (count > 0) {
//                long transferred = sourceChannel.transferTo(srcPos, count, destinationChannel);
//                srcPos += transferred;
//                destPos += transferred;
//                count -= transferred;
//            }
//
//            raf.getChannel().truncate(destPos);
//            raf.close();
//            raf = new RandomAccessFile("users", "rw");
//
//            reloadHashTable();
//
//            RandomAccessFile tempRaf = new RandomAccessFile("psnUsers", "rw");
//            psnUsers.seek(0);
//
//            srcPos = 0;
//            destPos = 0;
//
//            while (psnUsers.getFilePointer() < psnUsers.length()) {
//                String currentUsername = psnUsers.readUTF();
//                String trophyType = psnUsers.readUTF();
//                String trophyGame = psnUsers.readUTF();
//                String trophyName = psnUsers.readUTF();
//                long date = psnUsers.readLong();
//
//                long entryLength = 4L * (currentUsername.length() + trophyType.length() + trophyGame.length() + trophyName.length()) + 8L;
//
//                if (username.equals(currentUsername)) {
//                    srcPos += entryLength;
//                } else {
//                    tempRaf.seek(destPos);
//                    tempRaf.writeUTF(currentUsername);
//                    tempRaf.writeUTF(trophyType);
//                    tempRaf.writeUTF(trophyGame);
//                    tempRaf.writeUTF(trophyName);
//                    tempRaf.writeLong(date);
//                    destPos += entryLength;
//                }
//            }
//
//            tempRaf.getChannel().truncate(destPos);
//            tempRaf.close();
//            psnUsers = new RandomAccessFile("psnUsers", "rw");
//        } else {
//            System.Userinfo.println("[deactivate] not found");
//        }
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
    }

    public boolean addTrophiTo(String username, String trophyGame, String trophyName, Trophy type) {
        if (users.search(username) == -1) {
            return false;
        }

        try {
            psnUsers.seek(psnUsers.length());
            psnUsers.writeUTF(username);
            psnUsers.writeUTF(type.name());
            psnUsers.writeUTF(trophyGame);
            psnUsers.writeUTF(trophyName);

            Date currentDate = new Date();
            psnUsers.writeLong(currentDate.getTime());

            raf.seek(0);
            long currentPos = 0;
            while (raf.getFilePointer() < raf.length()) {
                currentPos = raf.getFilePointer();
                String currentUsername = raf.readUTF();

                if (!currentUsername.equals(username)) {
                    raf.skipBytes(4 * 2 + 1);
                    continue;
                }

                int trophyPoints = raf.readInt();
                int trophyCount = raf.readInt();
                boolean registered = raf.readBoolean();

                System.out.println(trophyPoints);
                System.out.println(trophyCount);
                System.out.println(registered);
                if (!registered) {
                    return false;
                }

                trophyPoints += type.points;
                trophyCount++;

                System.out.println(trophyPoints);
                System.out.println(trophyCount);

                raf.seek(currentPos);

                // Guardar nueva informacion del usuario
                raf.writeUTF(currentUsername);
                raf.writeInt(trophyPoints);
                raf.writeInt(trophyCount);
                raf.writeBoolean(true);
                raf.close();
                raf = new RandomAccessFile("usuario", "rw");
                return true;
            }

            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public String playerInfo(String username) {
        if (users.search(username) == -1) {
            System.out.println("eroor");
            return "";
        }

        try {
            String Userinfo = "";
            raf.seek(0);
            while (raf.getFilePointer() < raf.length()) {
                String currentUsername = raf.readUTF();
                int trophyPoints = raf.readInt();
                int trophyCount = raf.readInt();
                boolean registered = raf.readBoolean();

                if (!currentUsername.equals(username) || !registered) {
                    continue;
                }

                Userinfo = "USERS INFO: " + username + "\n----------------\n";
                Userinfo += "\nTrofeos obtenidos: " + trophyCount;
                Userinfo += "\nPuntos " + trophyPoints;
                Userinfo += "\nInformación de trofeos:\n";

                psnUsers.seek(0);
                while (psnUsers.getFilePointer() < psnUsers.length()) {
                    String currentPsnUsername = psnUsers.readUTF();
                    String trophyType = psnUsers.readUTF();
                    String trophyGame = psnUsers.readUTF();
                    String trophyName = psnUsers.readUTF();
                    long date = psnUsers.readLong();

                    if (currentPsnUsername.equals(username)) {
                        Userinfo += "\n\tTipo de trofeo: " + trophyType
                                + "\nNombre del trofeo: " + trophyName
                                + " \nNombre del juego: " + trophyGame
                                + " \nFecha:  " + new Date(date) + "\n";

                    }
                }
                Userinfo += "*************************************************************\n";
            }
            return Userinfo;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public void close() {
        try {

            raf.close();
            psnUsers.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
