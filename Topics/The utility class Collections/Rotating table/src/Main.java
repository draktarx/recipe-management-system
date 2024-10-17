import java.util.*;

class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<String> lst = new ArrayList<>();
        int n = scanner.nextInt();
        scanner.nextLine(); // just skip it :)

        for (int i = 0; i < n; i++) {
            lst.add(scanner.nextLine());
        }

        n = scanner.nextInt();
        Collections.rotate(lst, n);

        for (String item : lst) {
            System.out.println(item);
        }
    }

}
