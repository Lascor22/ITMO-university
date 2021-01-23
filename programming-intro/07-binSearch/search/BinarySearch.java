package search;

public class BinarySearch {
    public static void main(String[] args) {
        int key = Integer.parseInt(args[0]);
        int[] array = new int[args.length - 1];
        for (int i = 0; i < args.length - 1; i++) {
            int temp = Integer.parseInt(args[i + 1]);
            array[i] = temp;
        }
        int ans = BinSearch(array, key);
        System.out.print(ans);
    }

    // Pre: ∀i array[i] >= array[i - 1] && ∃i : array[i] = key
    private static int iterativeBinSearch(int[] array, int key) {
        // Pre
        int left = -1, right = array.length;
        // Inv: (right' == array.size | (right' < array.size && array[right'] <= key)) &
        // (left' == -1 ||(0 <= left' < array.size & array[left'] > key)) &
        // & (right'' - left'' <= (right' - left' + 1) div 2) & (right' - left' >= 1)
        while (left < right - 1) {
            // Inv & Pre right' > left' + 1
            int mid = (left + right) / 2;
            // Pre & Inv & right' > left' + 1 & m == (right' + left') div 2 & left' < m < right'
            if (array[mid] <= key) {
                // Pre & Inv & right' > left' + 1 & m == (right' + left') div 2 & left' < m < right' & array[mid] <= key
                right = mid;
                // right'' == mid & left'' = left'  ->  mid - left'' == (left' + right') div 2 - left' == (right' - left' + 1) div 2
                // left'' = left'  ->  (left'' == -1 | (0 <= left'' < array.size & array[left''] > key))
                // Pre & array[right'' == mid] <= key & left' < mid < right'  -> (right'' < array.size & array[right''] <= key)
                // right' > left' + 1 & right'' == mid & left' < mid < right'  ->   right'' - left'' >= 1
                // Pre & Inv
            } else {
                // Pre & Inv && right' > left' + 1 & mid = (right' + left') div 2 & array[mid] > key
                left = mid;
                // left'' == mid & right'' = right'  -> right'' - mid == right' - (right' + left') div 2 == (right' - left' + 1) div 2
                // right'' == right'  ->  ((right'' == array.size) | (right'' < array.size & array[right''] <= key))
                // Pre & array[left'' == mid] > key && left' < mid < right'  ->  (0 <= left'' < array.size & array[left''] > key)
                // right' > left' + 1 & left'' = mig & l' < m < r'  ->  r'' - l'' >= 1
                // Pre & Inv
            }
        }
        // !((right' - left') > 1) & Inv & (right' - left' >= 1) -> left' + 1 == right'
        // Pre & Inv & (left' + 1 == right')
        // if (array.size == 0) -> never visited while ->  right' == 0 & left' == -1
        // if (array.last > x) -> never visited if() in while()  ->  right' == array.size() & left' = array.size() - 1
        // if (right' < array.size && Inv) ->  a[right'] <= key
        // if right' == 0
        // 3b) right' > 0 & Inv & (left' + 1 == right') -> array[right' - 1] > key
        return right;
    }
    // Post: (array[i]' == array[i]) & (array.size == 0 && ans == 0) | (arrray.last > x && ans == array.size) |
    // (ans < array.size && array[ans] <= key & (ans == 0 | array[ans - 1] > key))

    // Pre: (array[i]' == array[i]) & (array[i] >= array[i + 1] ∀i) & (right == array.size | (right < array.size & array[r] < key)) &
    // & (left == -1 | (0 <= left < array.size & array[left] >= key)) & (right' - left' <= (right - left + 1) div 2) && (right - left >= 1)
    private static int BinarySearchRecursive(int[] array, int key, int left, int right) {
        if (right - left == 1) {
            // Pre & left + 1 == right
            // if array.size == 0 -> it is first level of recursion ->  right == 0 & left == -1
            // if array[0] < key -> never visited else() in previous recursions  -> right == 0 & left == -1
            // if left != -1  ->  array[left] >= key
            // if left == n - 1
            // if left < n - 1 & Inv & (left + 1 == right) -> array[left + 1] < key
            return left;
        } else {
            // Pre & right > left + 1
            int mid = (left + right) / 2;
            // Pre & right > left + 1 & mid == (left + right) div 2 & left < mid < right
            if (array[mid] < key) {
                // Pre & right > left + 1 & mid == (left + right) div 2 & left < mid < right & array[mid] < key
                // array[i]' == array[i] & array[i] >= array[i + 1] ∀i & key' == key
                // left' == left  ->  (left == -1 | (0 <= left < array.size & array[left] >= key))
                // Pre & array[mid] < key & left < mid < right  ->  (right < array.size & array[right] < key)
                // right' == mid & left' = left  ->  mid - left' == (left + right) div 2 - left == (right - left + 1) div 2
                // right > left + 1 & right' == mid & left < mid < right  ->   right' - light' >= 1
                // Pre ∀left' and ∀right'
                return BinarySearchRecursive(array, key, left, mid);
                // Post was OK before and a[i] hasn't changed ∀i
            } else {
                // Pre & right > left + 1 & mid = (right + left) div 2 & array[mid] >= key
                // array[i]' == array[i] & array[i] >= array[i + 1] ∀i & key' = key
                // left' == mid & right' = right  -> right' - mid == right - (right + left) div 2 == (right - left + 1) div 2
                // right' == right  -> ((right == array.size) || (right' < array.size & array[right'] < key))
                // Pre & array[left' == mid] >= key & left < mid < right  ->  (0 <= left' < array.size & array[left'] >= key)
                // right > left + 1 & left' = mid & left < mid < right ->  right' - left' >= 1
                // Pre for left' and right'
                return BinarySearchRecursive(array, key, mid, right);
                // Post was OK before and a[i] hasn't changed ∀i
            }
        }
    }
    // Post: (key' == key) & (array[i]' == array[i]) & (array.size == 0 | (array[0] < key)) & answer == -1) & (array[answer] >= key & (answer == array.size() - 1 | array[answer + 1] < key)
}