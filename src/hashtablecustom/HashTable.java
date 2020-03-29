import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * This file defines a HashTable class.  Keys are integer and values are string in the table .
 *  The default constructor creates a table 
 * that initially has 64 locations, but a different initial size can be specified 
 * as a parameter to the constructor.  The table increases in size if it 
 * becomes more than 3/4 full; to help implement this, a count of the number
 * of items in the hash table is kept in an instance variable.
 */
public class HashTable {

   private static class ListNode {
      int key;
      String value;
      ListNode next;  // Pointer to next node in the list;
                      
   }

   private ListNode[] table;  // The hash table, represented as an array of linked lists.
                              

   private int count;  // The number of (key,value) pairs in the hash table.
                       

   
   
   public HashTable() {
      table = new ListNode[64];
   }

   
   
   public HashTable(int initialSize) {
      if (initialSize <= 0)
         throw new IllegalArgumentException("Illegal table size");
      table = new ListNode[initialSize];
   }

   

   
   public void put(int key, String value) {
      
      
      
      int bucket = hash(key); // Which location should this key be in?
      
      ListNode list = table[bucket]; // For traversing the linked list
                                     // at the appropriate location.
      while (list != null) {
            // Search the nodes in the list, to see if the key already exists.
         if (list.key==key)
            break;
         list = list.next;
      }
      
     
      
      if (list != null) {
            // Since list is not null, we have found the key.
            // Just change the associated value.
         list.value = value;
      }
      else {
             // Since list == null, the key is not already in the list.
             // Add a new node at the head of the list to contain the
             // new key and its associated value.
         if (count >= 0.75*table.length) {
               // The table is becoming too full.  Increase its size
               // before adding the new node.
            resize();
            bucket = hash(key);  // Recompute hash code
                                 
         }
         ListNode newNode = new ListNode();
         newNode.key = key;
         newNode.value = value;
         newNode.next = table[bucket];
         table[bucket] = newNode;
         count++; 
      }
   }

   
   
   public String get(int key) {
      
      int bucket = hash(key);  // At what location should the key be?
      
      ListNode list = table[bucket];  // For traversing the list.
      while (list != null) {
            // Check if the specified key is in the node that
            // list points to.  If so, return the associated value.
         if (list.key==key)
            return list.value;
         list = list.next;  // Move on to next node in the list.
      }
      // Returning the value null to indicate that the key is not in the table.
      return null;  
   }
   public void search(String str){
       searchString(str);
   }
   private void searchString(String str){
       int key=getKey(str);
       int index=hash(key);
       int found=0;
       int count=0;
       ListNode list=table[index];
       while(list!=null){
           if(list.value.equals(str)){
               count++;
               found=1;
               break;
           }
           list=list.next;
           count++;
       }
       if(found==0)
           System.out.println("String not present in the HashTable;No of String inspected is"+count);
       else
           System.out.println("String present in the HashTbale;No of String inspected is:-"+count);
   }
   
   //checks if the key is already in the table or not
   public boolean containsKey(int key) {
      
      int bucket = hash(key);  //getting index of the key 
      
      ListNode list = table[bucket]; 
      while (list != null) {
         if (list.key==key)
            return true;
         list = list.next;
      }
      
     //if key doesn't exist return false
      
      return false;
   }

   //returns size of the HashTable means total no of (key:value) pairs
   public int size() {
      return count;
   }

   //compute the hashCode for the key
   //also gives index that at which index key should be 
   private int hash(Object key) {
      return (Math.abs(key.hashCode())) % table.length;
   }

   
   private void resize() {
      ListNode[] newtable = new ListNode[table.length*2];
      for (int i = 0; i < table.length; i++) {
         ListNode list = table[i]; // For traversing linked list number i.
         while (list != null) {
            ListNode next = list.next;  
            int hash = (Math.abs(list.hashCode())) % newtable.length;
            list.next = newtable[hash];
            newtable[hash] = list;
            list = next;  
         }
      }
      table = newtable;  
   } 
   String c=null;
   public int getKey(String str){
        int g=31;//i took 5 here because it will generate less no of keys
        int hash=0;
        for(int i=0;i<str.length();i++)
            hash=(g*hash)+str.charAt(i);
        return hash;
   }

	public static void main(String args[]) throws IOException{
		HashTable hashTable=new HashTable();
                String str="";
                str=new String(Files.readAllBytes(Paths.get("data.txt")));
                String[] words=str.split("[ .,]+");//you can add more special-characters 
                for(String word:words){
                        int key=hashTable.getKey(word);
                        System.out.println("Key:-"+key+"value:-"+word);
                        hashTable.put(key, word);
                 }
                 String str_search;
        Scanner scan=new Scanner(System.in);
        do{
            System.out.println("\npress '1' to search||press '0' to quit\n");
            int ch=(scan.nextInt());
            scan.nextLine();
             if(ch==1){
                System.out.println("\nenter string to be searched\n");
                str_search=scan.nextLine();
                hashTable.search(str_search);
            }else if(ch==0)
                    break;
        }while(true);
	}
} 
   