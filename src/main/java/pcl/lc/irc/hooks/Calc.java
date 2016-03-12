package pcl.lc.irc.hooks;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import pcl.lc.irc.AbstractListener;
import pcl.lc.irc.Config;
import pcl.lc.irc.IRCBot;

/**
 * @author Caitlyn
 *
 */
@SuppressWarnings("rawtypes")
public class Calc extends AbstractListener {
	
	@Override
	protected void initCommands() {
		IRCBot.registerCommand("calc", "Does basic math on the expression passed to the command Ex: " + Config.commandprefix + "calc 2+2");
	}

	@Override
	public void handleCommand(String sender, final MessageEvent event, String command, String[] args) {
		String prefix = Config.commandprefix;
		if (command.equals(prefix + "calc")) {
			String expression;
			expression = StringUtils.join(args," ");
			if (!IRCBot.isIgnored(event.getUser().getNick())) {
				if (expression.equalsIgnoreCase("the meaning of life")) {
					event.respond("42");
				} else {
					Expression e = new ExpressionBuilder(expression).build();
					double result = e.evaluate();
					NumberFormat formatter = new DecimalFormat("#,###.##");
					formatter.setRoundingMode(RoundingMode.DOWN);
					event.respond(formatter.format(result));
				}
			}
		}
	}
}