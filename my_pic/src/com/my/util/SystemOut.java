package com.my.util;

public class SystemOut {
   public static void out(String outStr){
	   if(Tools.isDebug)
	   System.out.println(outStr);
   }
}
