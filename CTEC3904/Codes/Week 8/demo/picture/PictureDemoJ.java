package demo.picture;
import java.util.ArrayList;

import lib.picture.Picture;

public class PictureDemoJ {

    /*
     * A small demo of calling the Picture library from within Java.
     * Try calling some other Picture methods and extend/modify the
     * program below.
     */
	
	public static void main(String[] args) {
	  Picture p1 = Picture.apply("This\nis\na\npicture");
      Picture p2 = Picture.apply("This\nis\nsomething\nelse");
      Picture p3 = Picture.apply("ABC\nDEF\nGHI");
      Picture p4 = Picture.apply("*");
      Picture p5 = p1.beside(p2).above(p3).beside(p4);
      
      ArrayList<Picture> ps = new ArrayList<Picture>();
      
      ps.add(p1); ps.add(p2); ps.add(p3);
      Picture p6 = Picture.spread(ps);
      System.out.println(p6);
      
	}

}
