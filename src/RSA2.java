import com.sun.org.apache.xpath.internal.operations.Mod;
import jdk.nashorn.internal.ir.SwitchNode;
import jdk.nashorn.internal.ir.WhileNode;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.spec.EncodedKeySpec;
import java.util.*;
import java.security.SecureRandom;

public class RSA2 {

    private BigInteger Gyorshatvany(BigInteger alap, BigInteger kitevo, BigInteger modulus) {
        BigInteger eredmeny = BigInteger.ONE;
        BigInteger apow = alap;

        while (!kitevo.equals(BigInteger.ZERO)) {
            if (kitevo.and(BigInteger.ONE).equals(BigInteger.ONE)) {

                eredmeny = (eredmeny.multiply(apow)).mod(modulus);
            }
            kitevo = kitevo.shiftRight(1);
            apow = apow.multiply(apow).mod(modulus);
        }
        return eredmeny;
    }

    private BigInteger randomszám(BigInteger min, BigInteger max) {
        BigInteger a;

        do {
            a = new BigInteger(min.bitLength(), new SecureRandom());
        } while (a.compareTo(min) < 0 || a.compareTo(max) > 0);

        return a;
    }


    private BigInteger Knumber(BigInteger min, BigInteger max) {
        BigInteger a;

        do {
            a = new BigInteger(min.bitLength(), new SecureRandom());
        } while (a.compareTo(min) < 0 || a.compareTo(max) > 0);

        return a;
    }

    private BigInteger generateSecureRandomNumber(Integer bitLength) {

        SecureRandom srg = new SecureRandom();
        return new BigInteger(bitLength, srg);
    }

    private boolean millerRabin(BigInteger szam, int tesztszam) {
        if (szam.equals(BigInteger.ZERO) || szam.equals(BigInteger.ONE) || szam.equals(new BigInteger("4")))

            return false;

        if (szam.equals(new BigInteger("2")) || szam.equals(new BigInteger("3")))


            return true;

        BigInteger m = szam.subtract(BigInteger.ONE);
        while (m.mod(new BigInteger("2")).equals(BigInteger.ZERO))
            m = m.divide(new BigInteger("2"));

        int i;
        BigInteger a = BigInteger.ZERO;
        BigInteger x = BigInteger.ZERO;

        for (i = 0; i < tesztszam; i++) {
            BigInteger max = szam.subtract(new BigInteger("2"));
            BigInteger min = new BigInteger("2");
            a = randomszám(min, max).add(min);

            x = Gyorshatvany(a, m, szam);

            if (x.equals(BigInteger.ONE) || x.equals(szam.subtract(BigInteger.ONE)))
                continue;

            while (!m.equals(szam.subtract(BigInteger.ONE))) {
                x = x.multiply(x).mod(szam);
                m = m.multiply(new BigInteger("2"));

                if (x.equals(BigInteger.ONE))
                    return false;

                if (x.equals(szam.subtract(BigInteger.ONE)))
                    continue;
            }
            return false;
        }
        return true;
    }


    private static BigInteger inverz(BigInteger a, BigInteger m) {
        BigInteger m0 = m;
        BigInteger x = BigInteger.ONE;
        BigInteger y = BigInteger.ZERO;
        if (m.equals(BigInteger.ONE))
            return BigInteger.ZERO;
        //ittjooooooo
        BigInteger q, b;
        while (a.compareTo(BigInteger.ONE) > 0) {
            //       System.out.println("HOLROSSZ1");
            q = a.divide(m);
            b = m;
            m = a.mod(m);
            a = b;
            b = y;
            y = x.subtract(q.multiply(y));
            x = b;
        }
        if (x.compareTo(BigInteger.ZERO) < 0)
            x = x.add(m0);
        //     System.out.println("HOLROSSZ2");
        return x;
    }


    public static BigInteger Euklidesz(BigInteger a, BigInteger b) {
        while (!b.equals(BigInteger.ZERO)) {
            BigInteger r = a.mod(b);
            a = b;
            b = r;
        }
        return a;
    }


    public static BigInteger Kibovitetteuk(BigInteger a, BigInteger b) {
        BigInteger x0 = BigInteger.ONE, x1 = BigInteger.ZERO, y0 = BigInteger.ZERO, y1 = BigInteger.ONE, x = null, y = null, n = BigInteger.ONE;


        while (!b.equals(BigInteger.ZERO)) {
            //    System.out.println("VEGTELEN");
            BigInteger r = a.mod(b);
            BigInteger q = a.divide(b);
            a = b;
            b = (r);
            x = x1;
            y = y1;
            x1 = q.multiply(x1).add(x0);
            y1 = q.multiply(y1).add(y0);
            x0 = x;
            y0 = y;
            n = n.negate();
        }
        x = n.multiply(x0);
        y = n.negate().multiply(y0);
        return a;
    }


    private BigInteger primgeneral() {
        BigInteger eredmeny = generateSecureRandomNumber(7);
        while (!millerRabin(eredmeny, 3))      //ITTJOMEG
            eredmeny = generateSecureRandomNumber(7);
        return eredmeny;
    }


    private List<BigInteger> clista(BigInteger uzenet, BigInteger p, BigInteger q, BigInteger d) {

        List<BigInteger> c = new ArrayList<>();

        BigInteger C1, C2, D1, D2;

        C1 = uzenet.mod(p);
        C2 = uzenet.mod(q);

        D1 = d.mod(p.subtract(BigInteger.ONE));
        D2 = d.mod(q.subtract(BigInteger.ONE));


        c.add(Gyorshatvany(C1, D1, p));
        c.add(Gyorshatvany(C2, D2, q));


        return c;
    }

    private List<BigInteger> mlista(BigInteger p, BigInteger q, BigInteger message, BigInteger d) {

        List<BigInteger> m = new ArrayList<>();

        m.add(p);
        m.add(q);

        return m;
    }

    private List<BigInteger> mlista2(BigInteger p, BigInteger q) {

        List<BigInteger> m2 = new ArrayList<>();

        m2.add(p);
        m2.add(q);

        return m2;
    }

    private BigInteger root(BigInteger p) {

        BigInteger mi = BigInteger.ZERO;
        BigInteger helperl = p.subtract(BigInteger.ONE);
        List<BigInteger> m2 = new ArrayList<>();

        for (BigInteger i =BigInteger.ONE; (i.compareTo(helperl) < 0) ; i = i.add(BigInteger.ONE)) {
            for (BigInteger j =BigInteger.ZERO; (j.compareTo(p) < 0) ; j = j.add(BigInteger.ONE)) {
                 m2.add(Gyorshatvany(i,j,p));
            }
        }
        BigInteger Chooser = generateSecureRandomNumber(10);
        int Number = Chooser.intValue();
        mi = m2.get(Number);

        BigInteger bi = BigInteger.valueOf(mi.intValue());


        return bi;
    }




    private static BigInteger Kinaimaradek(List<BigInteger> c, List<BigInteger> m) {
        BigInteger M = m.stream().reduce(BigInteger.ONE, BigInteger::multiply);
        BigInteger x = BigInteger.ZERO;

        for (int i = 0; i < c.size(); i++) {
            BigInteger mi = M.divide(m.get(i));
            BigInteger yi = inverz(mi, m.get(i));
            x = x.add(c.get(i).multiply(mi).multiply(yi));
        }


        x = x.mod(M);
        return x;
    }



    public static void main(String[] args) {

        String choice;
        BigInteger message;
        BigInteger message2;


        System.out.println("RSA vagy El-GAMAL titkositó");
        System.out.println("Használathoz az RSA illetve a GAMAL kulcsszavakat kell hasznalni");
        Scanner string = new Scanner(System.in);
        choice = string.nextLine();

        if ((choice.equals("RSA") || choice.equals("GAMAL")) == false) {
            System.out.println("Adja meg helyesen mit szeretne: (titkosit/visszafejt)");
            choice = string.nextLine();
        }

        switch (choice) {
            case "RSA":
                System.out.println("Adja meg az üzenetet:");
                Scanner s = new Scanner(System.in);
                message = s.nextBigInteger();


                BigInteger p, q, e, fi, n, d;

                RSA2 rsa2 = new RSA2();
                p = rsa2.primgeneral();
                q = rsa2.primgeneral();

                SecureRandom rand = new SecureRandom();
                int bitLength = 15;
                System.out.println("|----------------------------------------RSA szamolas--------------------------------------|");
                System.out.println(p + " elso prím");
                System.out.println(q + " masodik prím");


                n = p.multiply(q);
                System.out.println(n + " Q szorozva P értéke");

                BigInteger kivonas, kivonas2;
                kivonas = p.subtract(BigInteger.ONE);
                kivonas2 = q.subtract(BigInteger.ONE);
                fi = kivonas.multiply(kivonas2);

                System.out.println(fi + " Fi értéke");


                do {
                    e = BigInteger.probablePrime(bitLength / 2, rand);
                }
                while (!Euklidesz(fi, e).equals(BigInteger.ONE) && e.compareTo(fi) == -1);

                System.out.println(e + " E értéke");

                //       System.out.println("LEFUTIDAIG");

                d = inverz(e, fi);
                System.out.println("|----------------------------------------Kulcs & Encrypt--------------------------------------|");
                System.out.println(d + " D értéke");

                message = rsa2.Gyorshatvany(message, e, n);// ENCRYPT

                message2 = message;

                System.out.println(message + " kodolt szam");
                System.out.println("|----------------------------------------Gyorshatvany Decrytp--------------------------------------|");
                message = rsa2.Gyorshatvany(message, d, n);         //DECRYPT

                System.out.println(message + " a visszafejtett üzenet");
                System.out.println("|----------------------------------------Kinai Decrypt--------------------------------------|");

                BigInteger kinai;

                System.out.println(message2);

                List<BigInteger> c = rsa2.clista(message2, p, q, d);
                List<BigInteger> m = rsa2.mlista(p, q, message, d);

                System.out.println(c);
                System.out.println(m);

                kinai = Kinaimaradek(c, m);        //CTRDECRYPT

                System.out.println(kinai + " Kinai visszafejtett");


                break;
            case "GAMAL":
                System.out.println("Adja meg az üzenetet:");
                Scanner s2 = new Scanner(System.in);
                message = s2.nextBigInteger();


                BigInteger p2, g, h,c1,c2,k,titkoskulcs,helper,helpeeer,TWO,Khelper,test;





                RSA2 rsa = new RSA2();
                p2 = rsa.primgeneral();

                k = rsa.generateSecureRandomNumber(5);
                titkoskulcs= rsa.generateSecureRandomNumber(10);

                System.out.println("|----------------------------------------EL GAMAL szamolas--------------------------------------|");

                g = rsa.root(p2);


                h = rsa.Gyorshatvany(g,titkoskulcs,p2);
                System.out.println("A p értéke " + " " + p2);
                System.out.println("A k értéke " + " " + k);
                System.out.println("A h értéke " + " " + h);
                System.out.println("A g értéke " + " " + g);



                System.out.println("|----------------------------------------Kulcs & Encrypt--------------------------------------|");
                System.out.println(titkoskulcs + " a titkoskulcs");

                c1 = rsa.Gyorshatvany(g,k,p2);
                c2 = (message.multiply(rsa.Gyorshatvany(h,k,p2)));
                List<BigInteger> m2 = rsa.mlista2(c1, c2);


                System.out.println(m2 + " " + " Az Encrypted üzenet");// ENCRYPT


                System.out.println("|----------------------------------------Decrytp--------------------------------------|");

                helpeeer = (BigInteger.ONE.subtract((BigInteger.ONE.add(BigInteger.ONE))));
                helper = p2.subtract(helpeeer.subtract(p2));
                message = c2.multiply(rsa.Gyorshatvany(c1,helper,p2));

                System.out.println(message + " " + "A Decrypted üzenet ");
                break;

        }


        System.out.println("Lefutott");
    }

}


