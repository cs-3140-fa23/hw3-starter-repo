/*
 * Copyright statement at the bottom of the code.
 */

package sde.virginia.edu.hw3;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Displays the {@link State states} in a {@link Representation representation} by name, population, and allocated
 * representatives <b>in alphabetical order by state name</b>.
 *
 * @author Will-McBurney
 */
public class AlphabeticalFormat implements RepresentationFormat {
    /**
     * Generates table-like {@link String} of a {@link Representation} where states are sorted in alphabetical order
     * by name.
     *
     * @param representation an apportionment of representatives to the states
     * @return a {@link String} table for displaying a {@link Representation}
     * @see Representation#getFormattedString(RepresentationFormat)
     */
    @Override
    public String getFormattedString(Representation representation) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("State           |Population| Reps\n");
        var states = new ArrayList<>(representation.getStates());
        states.sort(Comparator.comparing(State::name, String.CASE_INSENSITIVE_ORDER));
        for (State state : states) {
            var stateString = getRepresentationStringForState(representation, state);
            stringBuilder.append(stateString);
        }
        return stringBuilder.toString();
    }

    private static String getRepresentationStringForState(Representation representation, State state) {
        return String.format("%-16s|%10d|%5d\n",
                state.name(), state.population(), representation.getRepresentativesFor(state));
    }
}


/*
 * Copyright (c) 2023. Paul "Will" McBurney <br>
 *
 * This software was written as part of an education experience by Prof. Paul "Will" McBurney at the University of Virginia, for the course CS 3140, Software Development Essentials. This source code, or any derivative source code (such as the student's own work building off this source code) is subject to the CS 3140 collaboration policy which can be found here: <a href="https://cs-3140-fa23.github.io/syllabus.html#homework-collaboration-policy">https://cs-3140-fa23.github.io/syllabus.html#homework-collaboration-policy</a>
 *
 * This source code and any derivative work may not be shared publicly through any means. This includes a prohibition on posting this work or derivative work on a public GitHub repository, course help website, file sharing platform, email, job application, etc. Sharing this code or derivative works with other students may be subject to referral to UVA Student Honor, as well as additional penalties.
 *
 * THE SOFTWARE IS PROVIDED &ldquo;AS IS&rdquo;, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */