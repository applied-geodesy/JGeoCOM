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

public enum TPS_DEVICE_CLASS {
	TPS_CLASS_TX60 (910, "Nova family member Tx60"),
	TPS_CLASS_TS16 (900, "Viva family member TS16"),
		
	TPS_CLASS_Tx50_1 	(651, "Nova family member Tx50,MS50, 1''"),
	TPS_CLASS_Tx50_0_5 	(650, "Nova family member Tx50, 0.5''"),
	
	TPS_CLASS_TS1X_1 	(600, "Viva TPS family member, 1''"),
	TPS_CLASS_TS1X_2 	(601, "Viva TPS family member, 2''"),
	TPS_CLASS_TS1X_3 	(602, "Viva TPS family member, 3''"),
	TPS_CLASS_TS1X_4 	(603, "Viva TPS family member, 4''"),
	TPS_CLASS_TS1X_5 	(604, "Viva TPS family member, 5''"),
	
	TPS_CLASS_TS01 	(500, "Mid Range family member, 1''"),
	TPS_CLASS_TS02 	(501, "Mid Range family member, 2''"),
	TPS_CLASS_TS03 	(502, "Mid Range family member, 3''"),
	TPS_CLASS_TS05 	(503, "Mid Range family member, 5''"),
	TPS_CLASS_TS06 	(504, "Mid Range family member, 6''"),
	TPS_CLASS_TS07 	(505, "Mid Range family member, 7''"),
	TPS_CLASS_TS10 	(506, "Mid Range family member, 10''"),

	TPS_CLASS_TDRA 	(350, "TDRA family member, 0.5''"),
	TPS_CLASS_Tx31 	(301, "Tx30 TS30,TM30 family member, 1''"),
	TPS_CLASS_Tx30 	(300, "Tx30 TS30,TM30 family member, 0.5''"),
	TPS_CLASS_1201 	(203, "TPS1200 family member, 1''"),
	TPS_CLASS_1205 	(202, "TPS1200 family member, 4''"),
	TPS_CLASS_1203 	(201, "TPS1200 family member, 3''"),
	TPS_CLASS_1202 	(200, "TPS1200 family member, 2''"),
	TPS_CLASS_1101 	(103, "TPS1100 family member, 1''"),
	TPS_CLASS_5100 	(8, "TPS5000 family member"),
	TPS_CLASS_1105 	(102, "TPS1100 family member, 5''"),
	TPS_CLASS_5005 	(7, "TPS5000 family member"),
	TPS_CLASS_1103 	(101, "TPS1100 family member, 3''"),
	TPS_CLASS_2003 	(6, "TPS2000 family member"),
	TPS_CLASS_1102 	(100, "TPS1100 family member, 2''"),
	TPS_CLASS_1500 	(5, "TPS1000 family member"),
	TPS_CLASS_6000 	(4, "TPS2000 family member"),
	TPS_CLASS_5000 	(3, "TPS2000 family member"),
	TPS_CLASS_1800 	(2, "TPS1000 family member, 0.3 mgon, 1''"),
	TPS_CLASS_1700 	(1, "TPS1000 family member, 0.5 mgon, 1.5''"),
	TPS_CLASS_1100 	(0, "TPS1000 family member, 1 mgon, 3''");

	private final int value;
	private String description;
	private TPS_DEVICE_CLASS(int value, String description) {
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

	public static TPS_DEVICE_CLASS getEnumByValue(int value) {
		for(TPS_DEVICE_CLASS element : TPS_DEVICE_CLASS.values()) {
			if(element.getValue() == value)
				return element;
		}
		return null;
	}  
}