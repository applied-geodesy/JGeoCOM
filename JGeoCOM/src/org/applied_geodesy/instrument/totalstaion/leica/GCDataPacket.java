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

package org.applied_geodesy.instrument.totalstaion.leica;

import org.applied_geodesy.instrument.totalstaion.leica.es.GRC;
import org.applied_geodesy.instrument.totalstaion.leica.es.RPC;

public class GCDataPacket {
	private RPC rpc;
	private GRC grc = GRC.GRC_UNDEFINED;
	private long timeOut = 15000L;
	private String argIn = null;
	private Object argOut = null;
	
	public GCDataPacket(RPC rpc) {
		this(rpc, null);
	}
	
	public GCDataPacket(RPC rpc, long timeOut) {
		this(rpc, null, timeOut);
	}
	
	public GCDataPacket(RPC rpc, String arg) {
		this(rpc, arg, 15000L);
	}
	
	public GCDataPacket(RPC rpc, String arg, long timeOut) {
		this.rpc = rpc;
		this.argIn = arg;
		this.timeOut = timeOut;
	}
	
	public RPC getRPC() {
		return rpc;
	}
	
	public String getArgument() {
		return this.argIn;
	}
	
	public Object getOnAnswerArgument() {
		return this.argOut;
	}
	
	void setOnAnswerArgument(Object arg) {
		this.argOut = arg;
	}
	
	void setGRC(GRC grc) {
		this.grc = grc;
	}
	
	public GRC getGRC() {
		return this.grc;
	}
	
	void setTimeout(long t) {
		this.timeOut = t;
	}
	
	public long getTimeout() {
		return this.timeOut;
	}
}
