/*
 * Copyright statement at the bottom of the code.
 */

package sde.virginia.edu.hw3;

import java.util.Arrays;
import java.util.List;


/**
 * This class handles interpreting the command-line arguments in {@link Apportionment#main(String[])}.
 * Currently, the following arguments format is supported:<br>
 * <code>&lt;filename&gt; [representatives] [--adams]</code>
 *
 * <ul>
 *     <li>filename is a required argument to specify the input file. Currently, .csv, .xlsx, and .xls are
 *     supported. See {@link Arguments#getStateSupplier()}</li>
 *     <li>[representatives] is an <b>optional</b> argument to specify the number of representatives to allocate.
 *     This number must be a positive integer. See {@link Arguments#getRepresentatives()}</li>
 *     <li>[--adams] is an <b>optional</b> argument to specify that you want to use the {@link AdamsMethod Adams
 *     apportionment Method}. By default, the {@link JeffersonMethod Jefferson apportionment method} is used.</li>
 * </ul>
 *
 * @author Will-McBurney
 */
public class Arguments {
    /**
     * The number of representatives in the US House of Representatives since 1913
     */
    public static final int DEFAULT_REPRESENTATIVE_COUNT = 435;
    private final List<String> arguments;

    /**
     * Constructor for {@link Arguments}
     *
     * @param args the command-line arguments passed into {@link Apportionment#main(String[])}
     */
    public Arguments(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("""
                    This program requires command line arguments:
                       java -jar Apportionment.jar <filename.csv> [number of representatives to allocate]""");
        }
        arguments = Arrays.asList(args);
    }

    /**
     * Gets a {@link StateSupplier} from the command-line arguments. {@link Arguments} currently supports
     * the following file formats:
     * <ul>
     *     <li>.csv - comma separated values (see {@link CSVStateReader}</li>
     *     <li>.xlsx and .xls - Excel, Google Sheets, and OpenOffice Calc (see {@link SpreadsheetStateReader}</li>
     * </ul>
     *
     * @return a StateSupplier associated with that file.
     * @throws UnsupportedFileFormatException if an invalid filename argument is provided
     */
    public StateSupplier getStateSupplier() {
        var filename = arguments.get(0);
        if (filename.toLowerCase().endsWith("csv")) {
            return new CSVStateReader(filename);
        }
        if (filename.toLowerCase().endsWith("xlsx") || filename.toLowerCase().endsWith("xls")) {
            return new SpreadsheetStateReader(filename);
        }
        throw new UnsupportedFileFormatException(filename);
    }

    /**
     * Determine the number of representatives to allocate form the command-line arguments.
     *
     * @return the number of representatives to allocate, based on the command-line arguments. If
     * <code>[representatives]</code> is not specified, then {@link Arguments#DEFAULT_REPRESENTATIVE_COUNT return the
     * default number of representatives}.
     */
    public int getRepresentatives() {
        if (arguments.size() == 1) {
            return DEFAULT_REPRESENTATIVE_COUNT;
        }

        try {
            var targetRepresentatives = Integer.parseInt(arguments.get(1));
            if (targetRepresentatives <= 0) {
                throw new IllegalArgumentException("Number of representatives argument must be a positive integer.");
            }
            return targetRepresentatives;
        } catch (NumberFormatException e) {
            return DEFAULT_REPRESENTATIVE_COUNT;
        }
    }

    /**
     * Determine the {@link ApportionmentMethod apportionment method} to use from the command-line arguments.
     * Specifically, if the arguments contain <code>--adams</code>, then the {@link AdamsMethod Adams apportionment
     * algorithm} is used. Otherwise, the {@link JeffersonMethod Jefferson apportionment method is used.}
     *
     * @return the {@link ApportionmentMethod apportionment method} to use when allocating representatives.
     */
    public ApportionmentMethod getApportionmentMethod() {
        if (arguments.contains("--adams")) {
            return new AdamsMethod();
        }
        return new JeffersonMethod();
    }

}

/*
 * Copyright (c) 2023. Paul "Will" McBurney <br>
 *
 * This software was written as part of an education experience by Prof. Paul "Will" McBurney at the University of Virginia, for the course CS 3140, Software Development Essentials. This source code, or any derivative source code (such as the student's own work building off this source code) is subject to the CS 3140 collaboration policy which can be found here: <a href="https://cs-3140-fa23.github.io/syllabus.html#homework-collaboration-policy">https://cs-3140-fa23.github.io/syllabus.html#homework-collaboration-policy</a>
 *
 * This source code and any derivative work may not be shared publicly through any means. This includes a prohibition on posting this work or derivative work on a public GitHub repository, course help website, file sharing platform, email, job application, etc. Sharing this code or derivative works with other students may be subject to referral to UVA Student Honor, as well as additional penalties.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */