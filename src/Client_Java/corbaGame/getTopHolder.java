package Client_Java.corbaGame;


/**
* corbaGame/getTopHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from corbaGame.idl
* Friday, 26 May 2023 11:05:21 PM SGT
*/

public final class getTopHolder implements org.omg.CORBA.portable.Streamable
{
  public String value[] = null;

  public getTopHolder ()
  {
  }

  public getTopHolder (String[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = getTopHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    getTopHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return getTopHelper.type ();
  }

}
