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

public enum TMC_INCLINE_PRG {
	TMC_PLANE_INC 	(2, "Use plane (apriori sigma)"),
	TMC_AUTO_INC 	(1, "Automatic mode (sensor/plane)"),
	TMC_MEA_INC 	(0, "Use sensor (apriori sigma)");
	
	private final int value;
	private String description;
	
	private TMC_INCLINE_PRG(int value, String description) {
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

	public static TMC_INCLINE_PRG getEnumByValue(int value) {
		for(TMC_INCLINE_PRG element : TMC_INCLINE_PRG.values()) {
			if(element.getValue() == value)
				return element;
		}
		return null;
	}  
}