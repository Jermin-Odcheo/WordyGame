package corbaGame;


/**
* corbaGame/notEnoughPlayers.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from corbaGame.idl
* Thursday, 17 July 2025 1:31:24 PM SGT
*/

public final class notEnoughPlayers extends org.omg.CORBA.UserException
{
  public String message = null;

  public notEnoughPlayers ()
  {
    super(notEnoughPlayersHelper.id());
  } // ctor

  public notEnoughPlayers (String _message)
  {
    super(notEnoughPlayersHelper.id());
    message = _message;
  } // ctor


  public notEnoughPlayers (String $reason, String _message)
  {
    super(notEnoughPlayersHelper.id() + "  " + $reason);
    message = _message;
  } // ctor

} // class notEnoughPlayers
