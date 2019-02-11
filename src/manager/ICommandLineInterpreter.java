package manager;

import java.io.File;

public interface ICommandLineInterpreter {
	/*
	 * Executes a command of the given protocol. The return value (String) is empty
	 * if the specification does not ask for a return value explicitly, e.g. the
	 * articlenumber of a product. Printing the return value to standard-out is not
	 * required as the operation will be called directly and the return value will
	 * be processed.
	 */
	public String execute(String command);
	/*
	 * Reads a given file on a line-by-line basis and interprets each line as a
	 * command of the protocol. The commands are executed consecutively. The
	 * string @@@ is replaced by the return value of the previous command. @@@ stays
	 * unaltered until some command produces a new return value.
	 */
	public void execute(File file);
}
