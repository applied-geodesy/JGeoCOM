/***********************************************************************
* Copyright by Michael Loesler, https://software.applied-geodesy.org   *
*                                                                      *
* This program is free software; you can redistribute it and/or modify *
* it under the terms of the GNU General Public License as published by *
* the Free Software Foundation; either version 3 of the License, or    *
* at your option any later version.                                    *
*                                                                      *
* This program is distributed in the hope that it will be useful,      *
* but WITHOUT ANY WARRANTY; without even the implied warranty of       *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the        *
* GNU General Public License for more details.                         *
*                                                                      *
* You should have received a copy of the GNU General Public License    *
* along with this program; if not, see <http://www.gnu.org/licenses/>  *
* or write to the                                                      *
* Free Software Foundation, Inc.,                                      *
* 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.            *
*                                                                      *
***********************************************************************/

package org.applied_geodesy.instrument.totalstaion.leica.es;

public enum TMC_MEASURE_PRG {
	TMC_FREQUENCY   	(11, "Frequency measurement (test)"),
	TMC_RED_TRK_DIST 	(10, "Starts the distance tracking measurement with red laser. This mode can be used for reflectorless short distance measurement or long distance measurement with reflector."),
	TMC_RTRK_DIST   	(8, "Starts the distance measurement in rapid tracking mode."),
	TMC_DO_MEASURE    	(6, "(Re)start measurement task"),
	TMC_SIGNAL      	(4, "Help mode for signal intensity measurement (use together with function @see TMC_GetSignal)"),
	TMC_CLEAR       	(3, "Stops the measurement and clears the data."),
	TMC_TRK_DIST    	(2, "Starts the distance measurement in tracking mode."),
	TMC_DEF_DIST    	(1, "Starts the distance measurement with the set distance measurement program."),
	TMC_STOP        	(0, "Stop measurement program");

	private final int value;
	private String description;
	
	private TMC_MEASURE_PRG(int value, String description) {
		this.value = value;
		this.description = description;
	}
	
	public int getValue() {
		return value;
	}
	
	public String toString() {
		return this.name() + "(" + this.value + "): " + this.description;
	}
	
	public String getDescription() {
		return this.description;
	}

	public static TMC_MEASURE_PRG getEnumByValue(int value) {
		for(TMC_MEASURE_PRG element : TMC_MEASURE_PRG.values()) {
			if(element.getValue() == value)
				return element;
		}
		return null;
	}  
}
