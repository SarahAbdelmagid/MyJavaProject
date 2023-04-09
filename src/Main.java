import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Member> eliteMembers = new ArrayList<>();
    static ArrayList<Member> generalMembers = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Üyelik Yöneticisine hoş geldiniz!");
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> addEliteMember();
                case 2 -> addGeneralMember();
                case 3 -> sendMailMenu();
                case 4 -> {
                    running = false;
                    System.out.println("  Güle güle!");
                }
                default -> System.out.println("\nGeçersiz seçim,Tekrar deneyin!");
            }
        }
    }

    public static void printMainMenu() {
        System.out.println("\n1- Elit üye ekleme");
        System.out.println("2- Genel Üye ekleme");
        System.out.println("3- Mail Gönderme");
        System.out.println("4- çıkış");
        System.out.print("\nSeçimini girin: ");
    }

    public static void addEliteMember() {
        System.out.print("İsim girin: ");
        String name = sc.nextLine();
        System.out.print("Soyadını girin: ");
        String surname = sc.nextLine();
        System.out.print("E-posta girin: ");
        String email = sc.nextLine();
        EliteMember member = new EliteMember(name, surname, email);
        eliteMembers.add(member);
        System.out.println("Elit üye eklendi!");
        saveMembersToFile();
    }

    public static void addGeneralMember() {
        System.out.println("İsim girin: ");
        String name = sc.nextLine();
        System.out.println("Soyadını girin: ");
        String surname = sc.nextLine();
        System.out.println("E-posta girin: ");
        String email = sc.nextLine();
        GeneralMember member = new GeneralMember(name, surname, email);
        generalMembers.add(member);
        System.out.println("Genel üye ekendi!");
        saveMembersToFile();
    }

    public static void sendMailMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n1- Email to elite members");
            System.out.println("2- Email to general members");
            System.out.println("3- Email to all members");
            System.out.println("4- Back to main menu");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> sendMailTo(eliteMembers);
                case 2 -> sendMailTo(generalMembers);
                case 3 -> {
                    ArrayList<Member> allMembers = new ArrayList<>();
                    allMembers.addAll(eliteMembers);
                    allMembers.addAll(generalMembers);
                    sendMailTo(allMembers);
                }
                case 4 -> running = false;
                default -> System.out.println("Geçersiz seçim,Tekrar deneyin!");
            }
        }
    }

    public static void sendMailTo(ArrayList<Member> members) {
        System.out.print("\nKonuyu girin: ");
        String subject = sc.nextLine();
        System.out.print("Mesaj girin: ");
        String message = sc.nextLine();
        for (Member member : members) {
            member.sendMail(subject, message);
        }
        System.out.println(""+ members.size() + "'üyeye e-posta gönder!");
    }

    public static void saveMembersToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("members.txt"));
            for (Member member : eliteMembers) {
                writer.write(member.getName() + "\t" + member.getSurname() + "\t" + member.getEmail() + "\n");
            }
            for (Member member : generalMembers) {
                writer.write(member.getName() + "\t" + member.getSurname() + "\t" + member.getEmail() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Üyeleri dosyaya kaydederken hata oluştu!");
        }
    }
}
abstract class Member {
    private final String name;
    private final String surname;
    private final String email;
    public Member(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public abstract void sendMail(String subject, String message);
}

class EliteMember extends Member {
    public EliteMember(String name, String surname, String email) {
        super(name, surname, email);
    }
    @Override
    public void sendMail(String subject, String message) {
        System.out.println("\nElit üyeye " + getName() + " " + getSurname() + " (" + getEmail() + "),"+" e-posta gönderiliyor"+"...");
        System.out.println("Konuyu girin: " + subject);
        System.out.println("Mesaj girin: " + message);

    }
}

class GeneralMember extends Member {
    public GeneralMember(String name, String surname, String email) {
        super(name, surname, email);
    }

    @Override
    public void sendMail(String subject, String message) {
        System.out.println("\ngeneral üyeye " + getName() + " " + getSurname() + " (" + getEmail() + "),"+" e-posta gönderiliyor"+"...");
        System.out.println("Konuyu girin: " + subject);
        System.out.println("Mesaj girin: " + message);

    }
}
