package test;
import java.io.FileNotFoundException;
import java.io.IOException;

import test.control.Conn;

public class PptReader {


public static void main(String[] args) throws FileNotFoundException, IOException{
	new PptReader();
}

public PptReader(){
	Conn co = new Conn();
	co.getConn();
}

}