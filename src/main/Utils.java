package main;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Utils {
	public static GridBagConstraints defaultConstraints(int col, int row) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = col;
		constraints.gridy = row;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(10, 10, 10, 10);
		return constraints;
	}
}
