/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newgame;

/**
 * This Class will handle the Turn Queue, we will instantiate an Array based
 * Queue, and sort the characters by using a Selection Sort algorithm. For
 * Simplicity We have limited the maximum number of Enemies in a Queue down to
 * 3. Two of which will be the dual main characters.
 *
 * @author Jaren Taylor
 */
public class QueueArrayBased implements QueueInterface {

    private final int MAX_SIZE = 3;
    public static CastMembers[] CastArray;
    private int front, back, count;

    public QueueArrayBased() {
        CastArray = new CastMembers[MAX_SIZE];
        front = 0;
        back = (MAX_SIZE - 1);
        count = 0;

    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean isFull() {
        return count == MAX_SIZE;
    }

    public int size() {
        return count;
    }
//removes players from the front of the queue

    public CastMembers dequeue() {
        //Precondition:The Queue is not empty.
        //PostCondition: The Queue is not empty.

        CastMembers next = CastArray[front];
        front = (front + 1) % (MAX_SIZE);
        --count;

        return next;

    }
//adds players to the back of the queue

    public void enqueue(CastMembers player) {
        back = (back + 1) % (MAX_SIZE);
        CastArray[back] = player;
        ++count;

    }
//allows you to look at the next upcoming player, but not modify its position

    public CastMembers peek() {
//preCondition: the Queue is not empty;
        return CastArray[front];
    }
    //this will sort the characters from largest to smallest, it is a modified version of the selection sort algorithm.
    //its broken into two parts, the swapping handled in castSort and the comparisons handled in indexOfLargest. 
    //Note that these do NOT account for enemies or players with equal speed stats, so any usage of those cases may require heavy modification.

    void castSort() {

        for (int last = (MAX_SIZE - 1); last >= 1; last--) {
            int largest = indexOfLargest(CastArray, (last + 1));

            CastMembers temp = CastArray[largest];
            CastArray[largest] = CastArray[last];
            CastArray[last] = temp;
        }

    }
//called within the castSort repeatedly until it is fully sorted through a pass equal to the count of players in the array.

    int indexOfLargest(CastMembers[] CastArray, int MAX_SIZE) {
        int currLargestIndex = 0;
        for (int currIndex = 1; currIndex < MAX_SIZE; currIndex++) {
            if (CastArray[currLargestIndex].getSPD() > CastArray[currIndex].getSPD()) {
                currLargestIndex = currIndex;
            }
        }

        return currLargestIndex;
    }

}
