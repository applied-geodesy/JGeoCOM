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

public enum FTR_DEVICETYPE {

	FTR_DEVICE_INTERNAL   (0, "Internal"),
	FTR_DEVICE_PCPARD     (1, "PC-Card"),
	FTR_DEVICE_SDCARD     (4, "SD-Card"),
	FTR_DEVICE_USB_MEMORY (5, "USB"),
	FTR_DEVICE_VOLATILERAM(6, "RAM");
	
	private final int value;
	private String description;
	
	private FTR_DEVICETYPE(int value, String description) {
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

	public static FTR_DEVICETYPE getEnumByValue(int value) {
		for(FTR_DEVICETYPE element : FTR_DEVICETYPE.values()) {
			if(element.getValue() == value)
				return element;
		}
		return null;
	}  
}
