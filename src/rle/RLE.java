
package rle;
import java.util.*;

public class RLE {
    static Scanner scan = new Scanner(System.in);
    
    public static void main(String[] args) {
        String s; // sirul caruia i se face compresia/ decompresia; 
        String alegere; // string-ul dupa care se hotaraste ce se executa
        System.out.print("Ce doriti sa efectuati? Compresie sau decompresie? Introduceti alegerea: ");
        alegere = scan.nextLine();  // citirea alegerii        
        if(alegere.toLowerCase().equals("compresie") == true){
            System.out.print("Introduceti cuvantul: ");
            s = scan.nextLine();    // citirea sirului asupra caruia se executa codul
            s = compresie(s);       // se apeleaza functia de compresie
            System.out.print("Sirul dupa compresie: ");
            System.out.println(s);  // sirul dupa compresie
        }
        else if(alegere.toLowerCase().equals("decompresie") == true){
            System.out.print("Introduceti cuvantul: ");
            s = scan.nextLine();    // citirea sirului asupra caruia se executa codul
            s = decompresie(s);     // se apeleaza functia de decompresie
            System.out.print("Sirul dupa decompresie: ");
            System.out.println(s);  // sirul dupa decompresie
        }
        else{
            System.out.println("Eroare la introducerea optiunii (compresie/decompresie)");
            System.exit(0);
        }
    }
    /*
        Compresia Run-Length Encoding: aaaabbbbcdee => 4a4b1c1d2e;
    
        !!! Acest tip de compresie este eficienta doar in cazul in care fiecare litera
        apare de cel putin 2 ori pentru a se evita cazul in care 
        compresia arata astfel: abcdef => 1a1b1c1d1e1f, caz in care 
        compresia are o lungime chiar mai mare decat sirul initial,
        ocupand chiar mai multa memorie.
        
        
        
    */
    public static String compresie(String s){
        StringBuffer sb = new StringBuffer("");
        int c = 0;
        char simbol = '0';  
        for(int i = 0; i < s.length(); i += c){ // se parcurge sirul din secventa in secventa        
            c = 0;  //c ul reprezinta lungimea fiecarei secvente
            for(int j = i; j < s.length(); j++){
                if(s.charAt(i) == s.charAt(j)){ // verifica faptul ca inca se afla in aceeasi secventa
                    c++;    //incrementeaza lungimea secventei
                    simbol = s.charAt(i);   // pastreaza simbolul din care este formata secventa
                }
                else{
                    break; // in cazul in care urmatorul caracter este diferit de cel curent se iese din a doua bucla
                            // si se trece la urmatoarea secventa
                }
            }
            sb.append(c);   // se introduce intr-un buffer lungimea secventei
            sb.append(simbol); // se introduce in buffer simbolul din care este formata secventa
        }
        return sb.toString();
    }
    /*
        Decompresia Run-Length Encoding: 4a4b10c1d2e; => aaaabbbbccccccccccdee;
   
    */
    public static String decompresie(String s1){
        StringBuffer sb = new StringBuffer("");
        List<String> list = new ArrayList<>();      //formez lista de simboluri
        List<Integer> aparitii = new ArrayList<>(); //formez lista de aparitii pt fiecare simbol lungimile listelor sunt egale
        String[] s = s1.split("");  // construiesc un sir de String-uri pe care sa il pot manipula mai usor folosind indexarea
        int c = 0;  // reprezinta saltul pe care il fac in interiorul sirului dupa preluarea numarului de aparitii si a simbolului
        for(int i = 0; i < s.length; i += c){
            int nrAparitii = 0, n = 0; 
            c = 1;
            for(int j = i; j < s.length; j++){
                try{
                    n = Integer.parseInt(s[j]);     
                    // se parcurge de la curent catre final sirul ce trebuie modificat
                    // in cazul in care in sir e o cifra, se incearca conversia la Integer,
                   // daca reuseste, nrAparitii = nrAparitii * 10 + n; c++;
                    // altfel, se va prinde exceptia aruncata la conversie
                }catch(Exception e){
                    list.add(s[j]);     //aici se prinde exceptia si se introduce simbolul ce precede numarul in lista de simboluri
                    break;              // se opreste pentru a se trece la urmatoarea secventa de tipul (aparitii,simbol);
                }
                nrAparitii = nrAparitii * 10 + n;   //aici se formeaza nrAparitii (ce poate fi si de mai mult de o cifra)
                c++;    // cand nrAparitii are dimensiune 1, trebuie salt peste 2 elemente 
                        // cand nrAparitii are dimensiune n, trebuie salt peste n+1 elemente (1 este dimensiunea simbolului);
                        // pt a ajunge la urmatorul nr de aparitii 
                          
            }
            aparitii.add(nrAparitii);   //se adauga in lista de aparitii toate nrAparitii gasite;
        }
        for(int i = 0 ; i < list.size(); i++){
            for(int j = 0; j < aparitii.get(i); j++){   //se parcurge lista de aparitii in paralel cu cea de simboluri
                                                        // simbolurile si numerele de aparitii sunt pe aceleasi pozitii in liste
                                                        //se afiseaza fiecare simbol de cate ori apare in lista de aparitii pe 
                                                        //pozitia corespunzatoare simbolului;
                sb.append(list.get(i));
            }
        }
        return sb.toString();
    }
}
