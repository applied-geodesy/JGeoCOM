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

public class FTR_BLOCK {
	public static final int FTR_MAX_BLOCKSIZE = 450;
	
	public byte FTR_BLOCK_val[];
	public int FTR_BLOCK_len;
	
	public FTR_BLOCK(byte[] fTR_BLOCK_val, int fTR_BLOCK_len) {
		FTR_BLOCK_val = fTR_BLOCK_val;
		FTR_BLOCK_len = fTR_BLOCK_len;
	}
}
