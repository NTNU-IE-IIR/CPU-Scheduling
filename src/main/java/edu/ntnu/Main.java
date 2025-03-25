package edu.ntnu;

import java.util.LinkedList;

public class Main {
  public static void main(String[] args) {
    // Create an ArrayList to hold the preEmptiveSchedulingList
    LinkedList<Process> preEmptiveSchedulingList = new LinkedList<>();
    LinkedList<Process> firstComeFirstServedList = new LinkedList<>();

    // Add preEmptiveSchedulingList to the list
    // BT: Burst Time, AT: Arrival Time
    preEmptiveSchedulingList.add(new Process(1, 0, 5, 2));
    preEmptiveSchedulingList.add(new Process(2, 2, 3, 1));
    preEmptiveSchedulingList.add(new Process(3, 3, 4, 3));
    preEmptiveSchedulingList.add(new Process(4, 5, 2, 2));
    preEmptiveSchedulingList.add(new Process(5, 6, 1, 4));

    for (Process process : preEmptiveSchedulingList) {
      firstComeFirstServedList.add(new Process(process.id, process.at, process.bt));
    }

    double[] firstComeFirstServed = CPUScheduling.firstComeFirstServed(firstComeFirstServedList);
    System.out.println("First Come First Served Scheduling:");
    System.out.println("Average Waiting Time: " + firstComeFirstServed[0]);
    System.out.println("Average Turnaround Time: " + firstComeFirstServed[1]);

    double[] preEmptivePriorityScheduling = CPUScheduling.preEmptivePriorityScheduling(preEmptiveSchedulingList);
    System.out.println("Pre-Emptive Priority Scheduling:");
    System.out.println("Average Waiting Time: " + preEmptivePriorityScheduling[0]);
    System.out.println("Average Turnaround Time: " + preEmptivePriorityScheduling[1]);
  }
}