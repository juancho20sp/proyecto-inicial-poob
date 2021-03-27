package mission;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
public class MissionContestTest {
    @Before
    public void setUp()
    {
    }
    @Test
    public void shouldSolveProblem(){
        MissionContest missionContest = new MissionContest();
        int[][] heights1 = {{1,4,0,5,2},{2,1,2,0,1},{0,2,3,4,4},{0,3,0,3,1},{1,2,2,1,1}};
        int[][] heights2 = {{50,20,3},{20,10,3}};
        assertEquals(9,missionContest.solve(heights1));
        assertEquals(30,missionContest.solve(heights2));

    }
    @After
    public void tearDown()
    {
    }
    
}
