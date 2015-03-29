/*
 * Copyright (c) 2011, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.gcm.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.yattatech.gcm.log.ILogCallback;

/**
 * Utility gui class responsible only for dispatching 
 * a message to gcm service
 *  
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 * 							     (adriano.alencar@hp.com)
 *
 */
public final class GCMGui extends JFrame implements ILogCallback {

	private static final long serialVersionUID = 6662038034086700180L;
	
	private static final int WIDTH  = 640;
	private static final int HEIGTH = 480;	
	private JTextField tfChannel;
	private JTextField tfMess;	
	private JRadioButton rbJson;
	private JRadioButton rbText;	
	private JTextArea txArea;	
	private volatile boolean running;
	
	public GCMGui() {
		
		setLayout(null);
		setTitle("GCM Sender");
		setSize(WIDTH, HEIGTH);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initializeComponents();
		GCMSender.logCallback = this;
	}
		
	private void initializeComponents() {
		
		final int padding  = 10;
		final int width    = 100;
		final int height   = 20;
		final int buttonW  = width * 3;		
		JLabel url         = new JLabel("Registration ID:");
		JLabel message     = new JLabel("Message:");
		JLabel json        = new JLabel("Json:");
		JLabel text        = new JLabel("Text:");
		tfChannel          = new JTextField();
		tfMess             = new JTextField();
		rbJson             = new JRadioButton();
		rbText             = new JRadioButton();
		txArea             = new JTextArea();
		JScrollPane scroll = new JScrollPane(txArea);
		ButtonGroup group  = new ButtonGroup();
		JButton sender     = new JButton();		
		JButton close      = new JButton();
		
		url.setToolTipText("Registration ID used to establish GCM Communication");
		url.setBounds(5, 10, width, height);		
		message.setBounds(5, 30, width, height);
		json.setBounds(5, 50, width, height);
		text.setBounds(5, 70, width, height);
		tfChannel.setBounds(120, 10, width * 5, height);
		tfMess.setBounds(120, 30, width * 5, height);
		rbJson.setBounds(120, 50, width * 5, height);
		rbJson.setSelected(true);
		rbText.setBounds(120, 70, width * 5, height);
		txArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		txArea.setEditable(false);		
		txArea.setLineWrap(true);
		scroll.setBounds(5, 90, getWidth() - 25, getHeight() - 180);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		group.add(rbJson);
		group.add(rbText);
		sender.setText("Send");
		sender.setToolTipText("Sends message do GCM");						
		sender.setBounds(5, HEIGTH - height * 4, buttonW, height);
		sender.addActionListener(new ActionListener() {

			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent event) {
				final String channel = tfChannel.getText();
				final String message = tfMess.getText();				
				if ((isEmpty(channel))||(isEmpty(message))) {
					JOptionPane.showMessageDialog(GCMGui.this, "<channel> <message> cannot be empty");
				} else {											
					running                  = true;					
					final String formatClass = rbJson.isSelected() ? "gcm.json.format" : "gcm.text.format"; 
					try {
						GCMSender.send(channel, message, formatClass);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(GCMGui.this, e.getMessage());
					} finally {
						running              = false;
						tfMess.setText("");
					}
				}				
			}
		});
				
		
		close.setText("Close");
		close.setBounds(sender.getX() + sender.getWidth() + padding, HEIGTH - height * 4, buttonW, height);
		close.addActionListener(new ActionListener() {
			
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if(running) {
					return;
				}
				WindowEvent windowEvent = new WindowEvent(GCMGui.this, WindowEvent.WINDOW_CLOSING);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(windowEvent);
			}
		});
		
		Container container = getContentPane();
		container.add(url);
		container.add(message);
		container.add(json);
		container.add(text);
		container.add(tfChannel);
		container.add(tfMess);
		container.add(rbJson);
		container.add(rbText);
		container.add(scroll);
		container.add(sender);
		container.add(close);
	}
		
    private static boolean isEmpty(String value) {
		if (value == null) return true;
		if ((value.isEmpty()) || (value.trim().isEmpty())) return true;
		return false;

    }
    
	/*
	 * (non-Javadoc)
	 * @see gcm.log.ILogCallback#message(java.lang.String)
	 */
	@Override
	public void message(String message) {
		txArea.append(message);
		txArea.append("\n");
	}		
	
	public static void main(String[] args) {
		new GCMGui().setVisible(true);
	}	
}
