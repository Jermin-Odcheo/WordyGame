package corbaGame;


/**
* corbaGame/GameException.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from corbaGame.idl
* Thursday, 17 July 2025 1:31:24 PM SGT
*/

public final class GameException extends org.omg.CORBA.UserException
{
  public String message = null;

  public GameException ()
  {
    super(GameExceptionHelper.id());
  } // ctor

  public GameException (String _message)
  {
    super(GameExceptionHelper.id());
    message = _message;
  } // ctor


  public GameException (String $reason, String _message)
  {
    super(GameExceptionHelper.id() + "  " + $reason);
    message = _message;
  } // ctor

} // class GameException
