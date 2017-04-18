/**
 * Copyright (c) 2014 by pw186.com.
 * All right reserved.
 */
package com.zw.jmsepc.silkie.jms.message.field;

import java.util.ArrayList;
import java.util.List;

import com.zw.jmsepc.silkie.jms.nio.IoBuffer;

/**
 * iobuffer操作帮助类
 *
 */
public class ByteProtocolSupport {
	
	protected IoBuffer buff;
	
///////////////////////////////////////////// util methods /////////////////////	
	protected byte readByte(){
		return buff.get();
	}
	
	protected void writeByte(byte b){
		buff.put(b);
	}
	
	protected boolean readBoolean() {
		return readByte() != 0x00;
	}
	
	protected void writeBoolean(boolean bool){
		writeByte(bool ? (byte)0x01 : 0x00);
	}
	
	protected char readChar(){
		return buff.getChar();
	}
	
	protected void writeChar(char ch){
		buff.putChar(ch);
	}
	
	protected short readShort(){
		return buff.getShort();
	}
	
	protected void writeShort(short s){
		buff.putShort(s);
	}
	
	protected void writeShort(int s){
		buff.putShort((short)s);
	}
	
	protected float readFloat(){
		return buff.getFloat();
	}
	
	protected void writeFloat(float f){
		buff.putFloat(f);
	}
	
	protected double readFloatD(){
		return Double.valueOf(Float.toString(readFloat()));
	}
	
	protected void writeFloatD(double d){
		writeFloat((float)d);
	}
	
	protected int readInt(){
		return buff.getInt();
	}
	
	protected void writeInt(int i){
		buff.putInt(i);
	}
	
	protected void writeUnsignedInt(long i){
		buff.putInt((int)i);
	}
	
	protected long readLong(){
		return buff.getLong();
	}
	
	protected void writeLong(long l){
		buff.putLong(l);
	}
	
	protected double readDouble(){
		return buff.getDouble();
	}
	
	protected void writeDouble(double d){
		buff.putDouble(d);
	}
	
	protected String readString(){
		byte[] temp = readByteArray();
		return temp == null? null : new String(temp);
	}
	
	protected void writeString(String str){
		writeByteArray(str == null? null : str.getBytes());
	}
	
	protected byte[] readByteArray(){
		int length = buff.getShort();
		if (length <= -1)
			return null;
		byte[] temp = new byte[length];
		buff.get(temp);
		return temp;
	}
	
	protected void writeByteArray(byte[] ba){
		if (ba == null) {
			buff.putShort((short)-1);
		} else {
			buff.putShort((short)ba.length);
			buff.put(ba);
		}
	}
	
	protected List<Integer> readListInt() {
		int length = buff.getShort();
		if (length <= -1)
			return null;
		
		ArrayList<Integer> list = new ArrayList<Integer>(length);
		for (int i = 0; i < length; i++) 
			list.add(buff.getInt());
		return list;
	}
	
	protected void writeListInt(List<Integer> list) {
		if (list == null) {
			buff.putShort((short)-1);
		} else {
			buff.putShort((short)list.size());
			for (int i = 0; i < list.size(); i++) 
				buff.putInt(list.get(i));
		}
	}
	
	protected List<Short> readListShort() {
		int length = buff.getShort();
		if (length <= -1)
			return null;
		
		ArrayList<Short> list = new ArrayList<Short>(length);
		for (int i = 0; i < length; i++) 
			list.add(buff.getShort());
		return list;
	}
	
	protected void writeListShort(List<Short> list) {
		if (list == null) {
			buff.putShort((short)-1);
		} else {
			buff.putShort((short)list.size());
			for (int i = 0; i < list.size(); i++) 
				buff.putShort(list.get(i));
		}
	}
	
	protected List<Float> readListFloat() {
		int length = buff.getShort();
		if (length <= -1)
			return null;
		
		ArrayList<Float> list = new ArrayList<Float>(length);
		for (int i = 0; i < length; i++) 
			list.add(buff.getFloat());
		return list;
	}
	
	protected void writeListFloat(List<Float> list) {
		if (list == null) {
			buff.putShort((short)-1);
		} else {
			buff.putShort((short)list.size());
			for (int i = 0; i < list.size(); i++) 
				buff.putFloat(list.get(i));
		}
	}	
	
	protected List<Double> readListDouble() {
		int length = buff.getShort();
		if (length <= -1)
			return null;
		
		ArrayList<Double> list = new ArrayList<Double>(length);
		for (int i = 0; i < length; i++) 
			list.add(buff.getDouble());
		return list;
	}
	
	protected void writeListDouble(List<Double> list) {
		if (list == null) {
			buff.putShort((short)-1);
		} else {
			buff.putShort((short)list.size());
			for (int i = 0; i < list.size(); i++) 
				buff.putDouble(list.get(i));
		}
	}	
	
	protected List<String> readListString() {
		int length = buff.getShort();
		if (length <= -1)
			return null;
		
		ArrayList<String> list = new ArrayList<String>(length);
		for (int i = 0; i < length; i++) 
			list.add(readString());
		return list;
	}
	
	protected void writeListString(List<String> list) {
		if (list == null) {
			buff.putShort((short)-1);
		} else {
			buff.putShort((short)list.size());
			for (int i = 0; i < list.size(); i++) 
				writeString(list.get(i));
		}
	}		
}
