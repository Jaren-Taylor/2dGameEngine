/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newgame;

/**
 * not all-inclusive list of functions to be used in queue array based, isFull
 * is included but not utilized really.
 *
 * @author Jaren Taylor
 */
public interface QueueInterface {

    public boolean isEmpty();// return true if stack is empty, false otherwise. 

    public boolean isFull();

    public int size();// counts the number of characters in the Queue.

    public CastMembers dequeue();// will remove a player from the queue, enabling them to attack.

    public void enqueue(CastMembers player); // Adds a character to the queue.

    public CastMembers peek(); // displays the next character who will move, if empty displays nothing.
}
