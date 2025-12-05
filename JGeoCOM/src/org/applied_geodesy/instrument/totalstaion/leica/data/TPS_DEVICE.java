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

package org.applied_geodesy.instrument.totalstaion.leica.data;

import org.applied_geodesy.instrument.totalstaion.leica.es.TPS_DEVICE_CLASS;
import org.applied_geodesy.instrument.totalstaion.leica.es.TPS_DEVICE_TYPE;

public class TPS_DEVICE {
	public TPS_DEVICE_CLASS DClass; // device precision class
	public TPS_DEVICE_TYPE Type;  // device configuration type 

	public TPS_DEVICE(TPS_DEVICE_CLASS dclass, TPS_DEVICE_TYPE type) {
		DClass = dclass;
		Type = type;
	}
}
