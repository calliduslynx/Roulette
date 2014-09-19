package de.mabe.roulette;

import java.util.Date;
import java.util.Vector;

public final class Debugger{
    public static final String NNL = "§NNL§";
    private static boolean fullDebug = false;
    private static boolean timeStamp = false;
    private static Vector switchList;
    static{
        switchList = new Vector();
    }

    private static class Switch{
        public String className;
        public boolean onOff;
    }
    private boolean newLine = true;
    private Object thisObj = null;
    public Debugger(Object thisObj){
        this.thisObj = thisObj;
        //System.out.println("NAME:"+thisObj.getClass().getName());
    }
    public static void setSwitch(String className, boolean onOff){
        Switch sw = getSwitch( className );
        if( sw == null ){
            sw = new Switch();
            sw.className = className;
            switchList.addElement( sw );
        }
        sw.onOff = onOff;
    }
    private static Switch getSwitch(String className){
        for( int i = 0; i < switchList.size(); i++ ){
            Switch sw = (Switch) switchList.elementAt( i );
            if( sw.className.equals( className ) )
                return sw;
        }
        return null;
    }
    public static void setFullDebug(boolean fullDebug){
        Debugger.fullDebug = fullDebug;
    }
    public static void setTimeStamp(boolean onOff){
        Debugger.timeStamp = onOff;
    }
    public void out(Object msgObj){
        if( msgObj instanceof Exception
            || fullDebug ){
            debugPrint( msgObj );
        } else {
            Switch sw = (thisObj == null) ? null : getSwitch( thisObj.getClass().getName() );
            if( sw == null || sw.onOff == true )
                debugPrint( msgObj );
        }
    }
    private void debugPrint(Object msgObj){
        StringBuffer msg = new StringBuffer();
        String thisName = ( thisObj == null ) ? "null" : thisObj.getClass().getName();
        thisName = thisName.substring( thisName.lastIndexOf( "." ) + 1 );

        if( msgObj instanceof Exception ){
            ( (Exception) msgObj ).printStackTrace();
            return;
        }

        //***** Details zu Beginn der Zeile
        if( newLine ){
            if( timeStamp ){                                  //*** Zeitstempel
                String time = new Date().toString();
                msg.append( "<" );
                msg.append( time.toString().substring( 11, 19 ) );
                msg.append( "> " );
            }
            msg.append( "[" );                              //*** Klassenname
            msg.append( thisName );
            msg.append( "] " );
        }


        //***** eigentliche Nachricht hinzufügen
        msg.append( msgObj.toString() );

        //***** Bearbeitung von \t
        int msgCounter = 0;
        StringBuffer newMsg = new StringBuffer( "" );
        boolean tabError = false;
        while( true ){
            char c = msg.charAt( msgCounter );
            if( c == '\t' ){
                try{
                    String tabString = msg.substring( msgCounter + 1, msg.indexOf( "\t", msgCounter + 1 ) );
                    int tab = Integer.parseInt( tabString );
                    msgCounter += 1 + tabString.length();
                    if( tab > 100 || tab < 1 )
                        throw new NumberFormatException();
                    for( ; newMsg.length() < tab; )
                        newMsg.append( " " );
                } catch( Exception x ) {
                    x.printStackTrace();
                    tabError = true;
                }
            } else {
                newMsg.append( c );
            }

            msgCounter++;
            if( msgCounter == msg.length() )
                break;
        }
        if(!tabError)msg = newMsg;


        //***** NNL am Ende checken
        if( msg.toString().endsWith( NNL ) ){                        //*** NNL am Ende?
            msg = new StringBuffer( msg.substring( 0, msg.length() - NNL.length() ) );
            newLine = false;
        } else {                                              //*** ohne NNL am Ende?
            msg.append( "\n" );
            newLine = true;
        }
        System.out.print( msg );
    }
}
