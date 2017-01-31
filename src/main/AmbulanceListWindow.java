package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public final class AmbulanceListWindow extends AppWindow {
	private JButton start;
	private JButton stop;
	private DataModel data;
	private JTable table;
	private int duration;
	private JTextField durationField;
	private JFrame map;
	
	private ArrayList<SwingWorker> workers = new ArrayList<SwingWorker>();
	
	public AmbulanceListWindow(DataModel data, JFrame map) {
		super(data, true);
		this.data = data;
		this.map = map;
		//super.setLayout(new GridBagLayout());
		super.setLayout(new FlowLayout());
		//super.setLayout(new BoxLayout(super.getWindow(),BoxLayout.Y_AXIS));
		GridBagConstraints constraints;
		
		table = new JTable(data.getAmbulances());
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		constraints = Utils.defaultConstraints(0, 0);
		constraints.gridwidth = 2;
		constraints.weighty = 6;
		//this.add(scrollPane, constraints);
		this.add(scrollPane);
		
		// add duration line
		JPanel row1 = new JPanel(new FlowLayout());
		JLabel durationLabel = new JLabel("Duration (seconds):");
		durationField = new JTextField("60", 20);
		durationField.setEditable(true);
		row1.add(durationLabel);
		row1.add(durationField);
		this.add(row1);
		
		// add start button
		start = new JButton("Start");
		start.setPreferredSize(new Dimension(150, 35));
		start.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	

            	try{
            		duration = Integer.parseInt(durationField.getText());
            	} catch (Exception ex){}
            	
            	stop.setEnabled(true);
            	start.setEnabled(false);
            	durationField.setEnabled(false);
            	
            	if (workers != null) {
        			for (SwingWorker worker : workers) {
        				worker.cancel(true);
        			}
        			workers.clear();
        		}
            	
            	// make worker threads
            	for(int i=0; i < data.getAmbulances().getRowCount(); i++){
            		
            		int ambulanceNum = i;
	            	SwingWorker worker = new SwingWorker<Void, Void>() {
	            		
	            		@Override
	    				public Void doInBackground() {
	            			for (int j=0; j < duration; j++){
	            				if (!isCancelled()) {
		            				data.nextMove(data.getAmbulances().get(ambulanceNum));
		            				publish();
		            				try {
		        						Thread.sleep(1000);
		        					} catch (InterruptedException e) {}
	            				}
	            			}
	            			return null;
	    				}            		
	            		@Override
	    				public void done() {
	            			data.getAmbulances().fireTableDataChanged();
	            			data.Save();
	            			stop.setEnabled(false);
	                    	start.setEnabled(true);
	                    	durationField.setEnabled(true);
	                    	map.repaint();
	    				}
	    				@Override
	    				protected void process(List<Void> chunks) {
	    					data.getAmbulances().fireTableDataChanged();
	    					map.repaint();
	    					
	    				}
	            		
	            	};
	            	
	            	workers.add(worker);
	            	worker.execute();
            	}
            }
        });		
		this.add(start);
		
		// add stop button
		stop = new JButton("Stop");
		stop.setPreferredSize(new Dimension(150, 35));
		stop.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	stop.setEnabled(false);
            	start.setEnabled(true);
            	durationField.setEnabled(true);
            	
            	// Kill threads
            	if (workers != null) {
        			for (SwingWorker worker : workers) {
        				worker.cancel(true);
        			}
        			workers.clear();        			
        		}
            	
            	// save to new CSV files
            	data.Save();
            }
		});
		stop.setEnabled(false);
		this.add(stop);
	}

	protected void refreshTable() {
		//TableModel model = new AmbulanceTableModel(data);
        //table.setModel(model);
		data.getAmbulances().fireTableDataChanged();
	}
}
