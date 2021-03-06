/**
 * 
 */
package pcl.lc.irc.hooks;

import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;
import pcl.lc.irc.AbstractListener;
import pcl.lc.irc.Command;
import pcl.lc.irc.IRCBot;
import pcl.lc.utils.DiceRollGroup;
import pcl.lc.utils.Helper;

/**
 * @author Caitlyn
 *
 */
@SuppressWarnings("rawtypes")
public class Dice extends AbstractListener {
	private Command local_command;

	@Override
	protected void initHook() {
		local_command = new Command("dice", 0) {
			@Override
			public void onExecuteSuccess(Command command, String nick, String target, GenericMessageEvent event, String params) {
				String s = params.trim();
				try {
					DiceRollGroup group = new DiceRollGroup(s);
					Helper.sendMessage(target, group.getResultString());
				} catch (Exception e) {
					e.printStackTrace();
					Helper.sendMessage(target, e.getMessage());
				}
			}
		}; local_command.setHelpText("Rolls dice. (eg 1d20)");
		local_command.registerAlias("roll");
		IRCBot.registerCommand(local_command);
	}

	public String chan;
	public String target = null;
	@Override
	public void handleCommand(String sender, MessageEvent event, String command, String[] args) {
		chan = event.getChannel().getName();
	}

	@Override
	public void handleCommand(String nick, GenericMessageEvent event, String command, String[] copyOfRange) {
		target = Helper.getTarget(event);
		local_command.tryExecute(command, nick, target, event, copyOfRange);
	}
}
