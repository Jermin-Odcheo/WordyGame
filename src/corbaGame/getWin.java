package corbaGame;


/**
* corbaGame/getWin.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from corbaGame.idl
* Thursday, 17 July 2025 1:31:24 PM SGT
*/

public final class getWin extends org.omg.CORBA.UserException
{
  public String message = null;

  public getWin ()
  {
    super(getWinHelper.id());
  } // ctor

  public getWin (String _message)
  {
    super(getWinHelper.id());
    message = _message;
  } // ctor


  public getWin (String $reason, String _message)
  {
    super(getWinHelper.id() + "  " + $reason);
    message = _message;
  } // ctor

} // class getWin
