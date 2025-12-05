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

import org.applied_geodesy.instrument.totalstaion.leica.es.AUT_ATR_MODE;
import org.applied_geodesy.instrument.totalstaion.leica.es.AUT_POS_MODE;
import org.applied_geodesy.instrument.totalstaion.leica.es.BOOLE;

public class AUT_CHANGE_FACE {
	public AUT_POS_MODE PosMode;
	public AUT_ATR_MODE ATRMode;
	public BOOLE bDummy = BOOLE.FALSE;
	
	
	public AUT_CHANGE_FACE(AUT_POS_MODE posMode, AUT_ATR_MODE aTRMode) {
		this(posMode, aTRMode, BOOLE.FALSE);
	}

	private AUT_CHANGE_FACE(AUT_POS_MODE posMode, AUT_ATR_MODE aTRMode,
			BOOLE bDummy) {
		PosMode = posMode;
		ATRMode = aTRMode;
		this.bDummy = bDummy;
	}
	
}
