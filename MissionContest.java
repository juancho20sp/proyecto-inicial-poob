import java.util.ArrayList;


public class MissionContest {

    /**
     * Solve the Problem C of ICPC 2017 
     * @param heights of the crates
     * @return maximum of crates that could be stolen
     */
    public int solve(int[][] heights){
        int[] visited = new int[heights[0].length];
        int[] match = new int[heights[0].length];

        for(int i=0; i<visited.length;i++){
            visited[i] = 0;
            match[i] = -1;
        }
        ArrayList<Integer> max_row = new ArrayList<>();
        ArrayList<Integer> max_col = new ArrayList<>();

        for(int i=0;i<heights.length; i++){
            max_row.add(maximumArray(heights[i]));
            if(i == heights.length-1){
                for(int j=0;j<heights[0].length;j++){
                    max_col.add(maximumArray(zip(heights)[j]));
                }

            }
        }
       int answer = sumOfRows(heights)-(heights.length*heights[0].length - countCoincidences(convertMatrixToArray(heights), 0));
        answer =answer - (sumArrayListElements(max_row) - (heights.length - countCoincidences(max_row, 0))) - (sumArrayListElements(max_col) - (heights[0].length - countCoincidences(max_col, 0)));
        for (int i=0;i<heights.length;i++){
            if(dfs(i,heights[0].length,visited,match,heights,max_row,max_col)){
                answer += max_row.get(i) - 1;
            }
        }
        return answer;
    }
    /**
     * Give the maximum value of an Array
     * @param arr the array
     * @return maximum value of the array
     */
    public int maximumArray(int[] arr){
        double infinity = Double.NEGATIVE_INFINITY;
        int maximum = (int) infinity;
        for(int i=0;i<arr.length;i++){
            if(arr[i] > maximum){
                maximum = arr[i];
            }
        }
        return maximum;
    }
    /**
     * Hungarian Algorithm
     * @param x
     * @return
     */
    public boolean dfs(int x,int m, int[] visited,int[] match,int[][] heights,ArrayList<Integer> max_row,ArrayList<Integer> max_col){
        for(int i=0; i<m;i++){
            if(visited[i] == 0 && heights[x][i] !=0 && max_row.get(x) == max_col.get(i)){
                visited[i] = 1;
                if(match[i] == -1 || dfs(match[i],m,visited,match,heights,max_row,max_col)){
                    match[i] = x;
                    return true;
                }
            }
        }
        return false;
        
    }
    /**
     * Sum of diferent rows
     * @param heights heights values
     * @return sum of all rows of heights
     */
    public int sumOfRows(int[][] heights){
        int sum = 0;

        for(int i=0;i<heights.length;i++){
            for(int j=0;j<heights[i].length;j++){
                sum += heights[i][j];
            }
        }
        return sum;
    }
    /**
     * convert a matrix into an Array
     * @param heights the matrix
     * @return the array with matrix values
     */
    public int[] convertMatrixToArray(int[][] heights){
        int[] result = new int[heights.length*heights[0].length];
        for(int i=0;i<heights.length;i++){
            int[] row = heights[i];
            for(int j = 0; j < row.length; j++) {
                result[i*row.length+j] = heights[i][j];
            }
        }
        return result;
    }
    /**
     * count the coincidences value into an Array
     * @param array the array we will search for
     * @param value to look coincidences
     * @return the number of coincidences into the array
     */
    public int countCoincidences(int[] array, int value){
        int count = 0;
        for(int i = 0;i<array.length;i++){
            if(array[i] == value){
                count++;
            }
        }
        return count;
    }
    /**
     * count the coincidences value into an ArrayList
     * @param array the ArrayList we will search for
     * @param value to look coincidences
     * @return the number of coincidences into the ArrayList
     */
    public int countCoincidences(ArrayList<Integer> array, int value){
        int count = 0;
        for(Integer i: array){
            if(i==value){
                count++;
            }
        }
        return count;
    }
    /**
     * Sum the values into in the ArrayList
     * @param array the ArrayList
     * @return the sum of all ArrayList values
     */
    public int sumArrayListElements(ArrayList<Integer> array){
        int sum = 0;
        for(Integer value : array){
            sum += value;
        }
        return sum;
    }
    /**
     * combines value per value in a matrix into a new matrix
     * @param heights Heights values
     * @return matrix with the values combined
     */
    public int[][] zip(int[][] heights){
        int[][] result = new int[heights[0].length][heights.length];
        for(int i = 0;i<result.length;i++){
            for(int j = 0; j<result[i].length;j++){
                result[i][j] = heights[j][i];
            }
        }
        return result;
    }
}
