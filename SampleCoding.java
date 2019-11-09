package sample;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.io.File;
class MonsterAttack{
	private String monsterName;
	private String attackLocation;
	private double damagesInMillionUSD;
	private Date date;
	
MonsterAttack(String name, String location,double damage,Date attackDate){
	monsterName=name;
	attackLocation=location;
	damagesInMillionUSD=damage;
	date= attackDate;
}
public String getMonsterName() {
	return monsterName;
	}
public void setMonsterName(String monsterName) {
	this.monsterName = monsterName;
	}
public String getAttackLocation() {
	return attackLocation;
	}
public void setAttackLocation(String attackLocation) {
	this.attackLocation = attackLocation;
	}
public double getDamagesInMillionUSD() {
	return damagesInMillionUSD;
	}
public void setDamagesInMillionUSD(double damagesInMillionUSD) {
	this.damagesInMillionUSD=damagesInMillionUSD;
	}
public Date getDate() {
	return date;
	}
public void setDate(Date date) {
	this.date = date;
}
public String toString() {
	return "monster name: "+monsterName +" attack location: "+attackLocation+" damages: "+damagesInMillionUSD+" Date: "+date;
}
}
class AttackMonitor{
Scanner sc = new Scanner(System.in);
private ArrayList<MonsterAttack> attack = new ArrayList<MonsterAttack>();

public void reportAttacks() throws FileNotFoundException 
{
	boolean exit = true;
	while(exit) {
		System.out.println("0)Quit 1)Add monster 2)Delete monster 3)Save to the file 4)Read the file");
		int option = sc.nextInt();
		sc.nextLine();
		switch(option){
		case 0 :
			exit=false;
			break;
		case 1:
			System.out.println("Enter the monster names: ");
			String monsterName =sc.nextLine();
			System.out.println("Enter the location that monsters attacked: ");
			String attackLocation = sc.nextLine();
			System.out.println("Enter the damages in million USD: ");
			double damage = sc.nextDouble();
			System.out.println("Enter the date(year) of attack ");
			int attackYear = sc.nextInt();
			System.out.println("Enter the date(month) of attack: ");
			int attackMonth = sc.nextInt();
			System.out.println("Enter the date(day) of attack : ");
			int attackDay = sc.nextInt();
			Date attackDate=new Date(attackYear-1900,attackMonth-1,attackDay);
			MonsterAttack monster = new MonsterAttack(monsterName,attackLocation,damage,attackDate);
			attack.add(monster);
			break;
		case 2:
			System.out.println("Enter the monster name");
			monsterName = sc.nextLine();
			boolean check = false;
			for(int i=0;i<attack.size();i++) {
				if(monsterName.equals(attack.get(i).getMonsterName())) 
				{
					attack.remove(i);
					check=true;
				}
			}
			if (check==false) {
				System.out.println("Cannot find that monster");
			}break;
		case 3:
			saveToFile();
			break;
		case 4:
			fileReader();
			break;
		}
	}
}
public void showAttacks() {
	for(int i=0;i<attack.size();i++) {
		System.out.println(attack.get(i).toString());
		}
}
public void showDamages() {
	double totalDamage = 0;
	for(int i =0;i<attack.size();i++) {
	totalDamage += attack.get(i).getDamagesInMillionUSD();
	}
	double meanDamage = totalDamage/attack.size();
	if (attack.size() == 0) {
		meanDamage = 0;
	}
	System.out.println("total Damage : "+totalDamage+" "+"mean Damage : "+meanDamage);
}
public void showMonsters() {
	int monsterNum = 0;
	String[] monsterNames = new String[attack.size()];
	int[] attackNumbers = new int[attack.size()];
	for(int i = 0;i<attack.size();i++) {
		boolean multiAttack = false;
		for (int j=0;j<monsterNames.length;j++) {
			if (monsterNames[j]==null) {
				continue;
			}
			if(monsterNames[j].equals(attack.get(i).getMonsterName())){
				attackNumbers[j] +=1;
				multiAttack = true;
			}
		}
		if (multiAttack == false){
			monsterNames[monsterNum] =attack.get(i).getMonsterName();
			attackNumbers[monsterNum] = 1;
			monsterNum +=1;
			}
	}
	for(int i=0;i<monsterNum;i++){
		System.out.println(monsterNames[i]+", "+attackNumbers[i]+" attack(s);");
	}
}
public void findEarliestAttack() {
	if(attack.size()==0) {
		return;
	}
	Date earliestDate = attack.get(0).getDate();
	int earliestAttack =0;
	for(int i=0;i<attack.size();i++) {
		if(attack.get(i).getDate().compareTo(earliestDate)<0) {
			earliestDate = attack.get(i).getDate();
			earliestAttack = i;
			}
		}
	System.out.println("The earliest attack is "+attack.get(earliestAttack).toString());
	
}
public void saveToFile() throws FileNotFoundException{
	System.out.println("Enter text file: ");
	Scanner sc = new Scanner(System.in);
	File file = new File(sc.next());
	if(file.exists()) {
		System.out.println("File already exists");
		return;
	}
	try {
		PrintWriter writer = new PrintWriter(file);
		for(int i=0;i<attack.size();i++) {
			writer.print(attack.get(i).getMonsterName()+",");
			writer.print(attack.get(i).getAttackLocation()+",");
			writer.print(attack.get(i).getDamagesInMillionUSD()+",");
			writer.println(new SimpleDateFormat("MM/dd/yyyy").format(attack.get(i).getDate()));
		}
		writer.close();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	System.out.println("Save the file successfully!");
}
public void fileReader() throws FileNotFoundException{
	attack.clear();
	System.out.println("Enter text file: ");
	Scanner sc = new Scanner(System.in);
	File file = new File(sc.next());
	if(!(file.exists())) {
		System.out.println("File does not exists");
		return;
	}
	Scanner freader = new Scanner(file);
	String line;
	String[]fields;
	String monsterName;
	String attackLocation;
	double damagesInMillionUSD;
	Date date;
	while(freader.hasNext()) {
		line=freader.nextLine();
		fields = line.split(",");	
		monsterName = fields[0];
		attackLocation = fields[1];
		damagesInMillionUSD = Double.parseDouble(fields[2]);
		String[] dates = fields[3].split("/");
		date = new Date(Integer.parseInt(dates[2])-1900,Integer.parseInt(dates[0])-1,Integer.parseInt(dates[1]));
		attack.add(new MonsterAttack(monsterName,attackLocation,damagesInMillionUSD, date));
	}
	freader.close();
	}
}
public class SampleCoding {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc= new Scanner(System.in);
		AttackMonitor attackMonitor = new AttackMonitor();
		attackMonitor.reportAttacks();
		attackMonitor.showAttacks();
		attackMonitor.showDamages();
		attackMonitor.showMonsters();
		attackMonitor.findEarliestAttack();
		
		}

}
