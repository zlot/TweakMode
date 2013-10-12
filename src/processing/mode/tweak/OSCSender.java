package processing.mode.tweak;

import java.net.InetAddress;
import java.util.ArrayList;

import com.illposed.osc.*;

public class OSCSender {
	
	public static void sendFloat(int index, float val, int port) throws Exception
	{
		OSCPortOut sender = new OSCPortOut(InetAddress.getByName("localhost"), port);
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(index));
		args.add(new Float(val));
		OSCMessage msg = new OSCMessage("/tm_change_float", args);
		 try {
			sender.send(msg);
		 } catch (Exception e) {
			 System.out.println("TweakMode: error sending new value of float " + index);
		 }
	}
	
	public static void sendInt(int index, int val, int port) throws Exception
	{
		OSCPortOut sender = new OSCPortOut(InetAddress.getByName("localhost"), port);
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(index));
		args.add(new Integer(val));
		OSCMessage msg = new OSCMessage("/tm_change_int", args);
		 try {
			sender.send(msg);
		 } catch (Exception e) {
			 System.out.println("TweakMode: error sending new value of int " + index);
			 System.out.println(e.toString());
		 }
	}

	public static void sendLong(int index, long val, int port) throws Exception
	{
		OSCPortOut sender = new OSCPortOut(InetAddress.getByName("localhost"), port);
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(index));
		args.add(new Long(val));
		OSCMessage msg = new OSCMessage("/tm_change_long", args);
		 try {
			sender.send(msg);
		 } catch (Exception e) {
			 System.out.println("TweakMode: error sending new value of long " + index);
			 System.out.println(e.toString());
		 }
	}
	
	
//////////////////////////////////////////////
	/////////////// MY ADDITIONS //////////////
	public static void sendCreatureBody(String creatureClass, String creatureBody, int port) throws Exception
	{
		OSCPortOut sender = new OSCPortOut(InetAddress.getByName("localhost"), port);
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new String(creatureClass));
		args.add(new String(creatureBody));
		OSCMessage msg = new OSCMessage("/tm_change_creature_body", args);
		 try {
			sender.send(msg);
		 } catch (Exception e) {
			 System.out.println("TweakMode: error sending new value of String: " + creatureClass);
			 System.out.println(e.toString());
		 }
	}
	public static void sendCreatureLimbManager(String creatureClass, String creatureLimbManager, int port) throws Exception
	{
		OSCPortOut sender = new OSCPortOut(InetAddress.getByName("localhost"), port);
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new String(creatureClass));
		args.add(new String(creatureLimbManager));
		OSCMessage msg = new OSCMessage("/tm_change_creature_limb", args);
		try {
			sender.send(msg);
		} catch (Exception e) {
			System.out.println("TweakMode: error sending new value of String: " + creatureClass);
			System.out.println(e.toString());
		}
	}
	
	public static void sendBehaviour(String creatureClass, String behaviour, boolean addBehaviour, int port) throws Exception
	{
		OSCPortOut sender = new OSCPortOut(InetAddress.getByName("localhost"), port);
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new String(creatureClass));
		args.add(new String(behaviour));
		
		OSCMessage msg;
		if(addBehaviour == true) 
			msg = new OSCMessage("/tm_add_behaviour", args);
		else
			msg = new OSCMessage("/tm_remove_behaviour", args);
			
		try {
			sender.send(msg);
		} catch (Exception e) {
			System.out.println("TweakMode: error sending new value of String: " + creatureClass);
			System.out.println(e.toString());
		}
	}
	
}
