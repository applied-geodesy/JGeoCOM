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

import org.applied_geodesy.instrument.totalstaion.leica.es.BAP_PRISM_TYPE;

public class BAP_PRISM_TYPE_AND_NAME {
	public BAP_PRISM_TYPE rePrismType;
	public String szPrismName;
	public BAP_PRISM_TYPE_AND_NAME(BAP_PRISM_TYPE rePrismType,
			String szPrismName) {
		this.rePrismType = rePrismType;
		this.szPrismName = szPrismName;
	}
}
