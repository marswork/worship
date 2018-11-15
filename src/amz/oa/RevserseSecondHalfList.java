
public class RevserseSecondHalfList {
    static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
            next = null;
        }
    }

    /**
     * Reverse second half of a singly linked list
     * @param head head of the list to be reversed of its 2nd half
     * @return head of the reversed 2nd half of the list
     */
    public static ListNode reverseSecondHalf(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // Step 1: find the previous node to middle node
        ListNode prevToMiddle = getPrevToMiddle(head);
        if (prevToMiddle == null) return head;

        // Step 2. Separate first half from second half
        ListNode second = prevToMiddle.next;
        prevToMiddle.next = null;

        // Step 3: Reverse the second half
        ListNode secondHead = reverse(second);

        // Step 4: Connect two halves
        prevToMiddle.next = secondHead;

        return head;

    }

    // Return the prev node to the middle node
    public static ListNode getPrevToMiddle(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode fast = head, slow = head, prev = head;
        while (fast != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next;
            if (fast != null) fast = fast.next;
        }

        return prev;
    }

    public static ListNode reverse(ListNode head) {
        ListNode prev = null, cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }

    public static ListNode makeList(int[] values) {
        ListNode dummyHead = new ListNode(0);
        ListNode cur = dummyHead;

        for (int x : values) {
            ListNode node = new ListNode(x);
            cur.next = node;
            cur = node;
        }

        return dummyHead.next;
    }

    public static void printList(ListNode head) {
        ListNode cur = head;
        if (cur != null) {
            System.out.print(cur.val);
            cur = cur.next;
        }

        while (cur != null) {
            System.out.print("->" + cur.val);
            cur = cur.next;
        }
        System.out.println();

    }

    public static void main(String[] args) {
        int[] values = {1, 2, 3, 4, 5};

        ListNode head = makeList(values);
        System.out.print("Origin: ");
        printList(head);

        System.out.print("Prev to middle: ");
        ListNode mid = getPrevToMiddle(head);
        printList(mid);

        System.out.print("Reversed 2nd half: ");
        head = reverseSecondHalf(head);
        printList(head);


        int[] values2 = {1, 2, 3, 4, 5, 6};

        ListNode head2 = makeList(values2);
        System.out.print("Origin: ");
        printList(head2);

        ListNode mid2 = getPrevToMiddle(head2);
        System.out.print("Prev to middle: ");
        printList(mid2);

        head2 = reverseSecondHalf(head2);
        System.out.print("Reversed 2nd half: ");
        printList(head2);


        ListNode head3 = reverseSecondHalf(null);
        System.out.print("Reversed 2nd half: ");
        printList(head3);

    }

}
