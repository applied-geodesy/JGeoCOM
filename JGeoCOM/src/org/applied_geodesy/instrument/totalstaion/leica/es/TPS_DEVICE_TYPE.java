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

public enum TPS_DEVICE_TYPE {
	TPS_DEVICE_T    	(0x00000, "Theodolite without built-in EDM"),
	TPS_DEVICE_MOT  	(0x00004, "Motorized device"),
	TPS_DEVICE_ATR  	(0x00008, "Automatic Target Recognition"),
	TPS_DEVICE_EGL  	(0x00010, "Electronic Guide Light"),
	TPS_DEVICE_DB   	(0x00020, "reserved (Database, not GSI)"),
	TPS_DEVICE_DL   	(0x00040, "Diode laser"),
	TPS_DEVICE_LP   	(0x00080, "Laser plumbed"),
	TPS_DEVICE_TC1  	(0x00001, "tachymeter (TCW1)"),
	TPS_DEVICE_TC2  	(0x00002, "tachymeter (TCW2)"),
	TPS_DEVICE_TC   	(0x00001, "tachymeter (TCW3)"),
	TPS_DEVICE_TCR  	(0x00002, "tachymeter (TCW3 with red laser)"),
	TPS_DEVICE_ATC  	(0x00100, "Autocollimation lamp (used only PMU)"),
	TPS_DEVICE_LPNT 	(0x00200, "Laserpointer"),
	TPS_DEVICE_RL_EXT 	(0x00400, "Reflectorless EDM with extended range (Pinpoint R100,R300)"),
	TPS_DEVICE_PS   	(0x00800, "Power Search"),
	TPS_DEVICE_SIM   	(0x04000, "runs on Simulation, no Hardware");
	
	private final int value;
	private String description;
	private TPS_DEVICE_TYPE(int value, String description) {
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

	public static TPS_DEVICE_TYPE getEnumByValue(int value) {
		for(TPS_DEVICE_TYPE element : TPS_DEVICE_TYPE.values()) {
			if(element.getValue() == value)
				return element;
		}
		return null;
	}  
}
