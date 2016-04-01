/*
 * Wordpress-java https://github.com/canbican/wordpress-java/ Copyright
 * 2012-2015 Can Bican <can@bican.net> See the file 'COPYING' in the
 * distribution for licensing terms.
 */
package net.bican.wordpress.configuration;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.configuration.BaseConfiguration;

/**
 * Implement a simple CLI configuration in apache commons configuration style
 *
 * @author Can Bican
 */
public class CliConfiguration extends BaseConfiguration {
  /**
   * generates the configuration in terms of arguments and options
   *
   * @param args
   *          Command line arguments
   * @param options
   *          Command line options
   * @throws ParseException
   *           When the configuration cannot be parsed
   */
  @SuppressWarnings("nls")
  public CliConfiguration(final String[] args, final Options options)
      throws ParseException {
    final CommandLineParser parser = new BasicParser();
    final CommandLine commandLine = parser.parse(options, args);
    for (final Option option : commandLine.getOptions()) {
      final String key = option.getLongOpt();
      final String val = option.getValue();
      if (val == null) {
        this.addProperty(key, "N/A");
      } else {
        this.addProperty(key, val);
      }
    }
  }
}
