package search;

public class BinarySearchMissing {
    // Pre: ∀i array[i] <= array[i - 1] & ∀i : array[i] is integer & key is integer
    private static int iterativeBinSearch(int[] array, int key) {
        // Pre
        int left = -1, right = array.length;
        // Inv: (right' == array.size | (right' < array.size & array[right'] <= key)) &
        // (left' == -1 | (0 <= left' < array.size & array[left'] >= key)) &
        // & (right'' - left'' <= (right' - left' + 1) div 2) & (right' - left' >= 1)
        while (left < right - 1) {
            // Inv & Pre & right' > left' + 1
            int mid = (left + right) / 2;
            // Pre & Inv & right' > left' + 1 & mid == (right' + left') div 2 & left' <= mid <= right'
            if (array[mid] > key) {
                // Pre & Inv & right' > left' + 1 & array[mid] > key
                left = mid;
                // left'' == mid & right'' = right
                // array[left''] > key &&  left'' <= result <= right''
                // Pre & Inv
            } else {
                // Pre & Inv && right' > left' + 1 & array[mid] <= key & left'' <= result <= right''
                right = mid;
                // right'' == mid & left'' == left
                // array[right''] <= key & left'' <= result <= right''
                // Pre & Inv
            }
        }
        // Pre & Inv & (left' + 1 == right')
        // result == right' &  ((array[right'] - most close over key | array[right'] == key) |
        // | (right == -1 && array[0] < key) | (right == array.size & array[array.size] > key))
        if (right != array.length && array[right] == key) {
            // array[right'] == key & result == right'
            return right;
        } else {
            // array[right'] most close over key | array.first < key | array.last > key
            return (-right - 1);
        }
    }
    // Post: (array[i]' == array[i]) &  (result == 0 & array.size == 0 ) | (array.last > key &
    // & result == - (array length - 1) - 1) | (result < array.size && array[result] <= key & (result == 0  |
    // | array[result - 1] > key))

    // Pre: ∀i array[i] <= array[i - 1] & ∀i : array[i] is integer & key is integer & right == array.size |
    // | (array[right] <= key & 0 <= right < array.size) & left == -1 |  (array[left] >= key && array.size > left) &
    // & left' - right' <= (right - left + 1) / 2 & right - left >= 1
    private static int recBinarySearch(int[] array, int left, int right, int key) {
        // Pre
        if (left >= right - 1) {
            // Pre & left >= right - 1
            // array[right] <= key & key - array[right + z] >= key - array[right] ∀z > 0 &
            // & array[right - z] > key ∀z > 0 & result == right
            if (right != array.length && right != -1 && array[right] == key) {
                // array[right] == key
                return right;
            } else {
                // array[right] most close over key | key > any array element
                // | key < any array element
                return (-right - 1);
            }
        } else {
            // Pre & left < right - 1
            int mid = (left + right) / 2;
            // left < mid < right & mid == (left + right) / 2
            if (array[mid] > key) {
                // left' == mid
                // array[left'] > key & mid <= result <= right
                return recBinarySearch(array, mid, right, key);
            } else {
                // right' == mid
                // array[right'] <= key & left <= result <= mid
                return recBinarySearch(array, left, mid, key);
            }
        }
    }
    // Post: (key' == key) & (array[i]' == array[i]) & (array.size == 0 | (array.first < key)) & result == -(-1) - 1) &
    // & (array[result] >= key & (result == -(array.size - 1)-1 | array[result + 1] < key)

    public static void main(String[] args) {
        int[] array;
        int key;
        try {
            key = Integer.parseInt(args[0]);
            array = new int[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                array[i - 1] = Integer.parseInt(args[i]);
            }
            //System.out.println(recBinarySearch(array, -1, array.length, key));
            System.out.println(iterativeBinSearch(array, key));
        } catch (NumberFormatException e) {
            System.err.println("Illegal arguments, not numbers");
        }
    }
}
