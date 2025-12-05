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

public enum BAP_PRISM_TYPE {
	BAP_PRISM_MA_MPR122 	(12, "prism type: MPR122 360º Prism for Machine Guidance"),
	BAP_PRISM_GRZ121_ROUND 	(11, "prism type: GRZ121 360º Prism for Machine Guidance"),
	BAP_PRISM_NDS_TAPE  	(10, "prism type: Leica HDS Target"),
	BAP_PRISM_USER      	(9, "prism type: user defined"),
	BAP_PRISM_MINI_ZERO 	(8, "prism type: mini zero"),
	BAP_PRISM_360_MINI  	(7, "prism type: 360 mini"),
	BAP_PRISM_USER3     	(6, "not supported by TPS1200"),
	BAP_PRISM_USER2     	(5, "not supported by TPS1200"),
	BAP_PRISM_USER1     	(4, "not supported by TPS1200"),
	BAP_PRISM_360       	(3, "prism type: 360"),
	BAP_PRISM_TAPE      	(2, "prism type: tape"),
	BAP_PRISM_MINI      	(1, "prism type: mini"),
	BAP_PRISM_ROUND     	(0, "prism type: round");
	
	private final int value;
	private String description;
	private BAP_PRISM_TYPE(int value, String description) {
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

	public static BAP_PRISM_TYPE getEnumByValue(int value) {
		for(BAP_PRISM_TYPE element : BAP_PRISM_TYPE.values()) {
			if(element.getValue() == value)
				return element;
		}
		return null;
	}  
}
