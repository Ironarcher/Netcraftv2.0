package com.nitrogenegames.netcraft.net;

import java.util.ArrayList;

public interface INet { //THIS CLASS IS A WIP
	ArrayList getConnected(); //USED TO GET CONNECTED BLOCKS
	int[] getCore(); //RETURNS CORE'S COORDINATES WHERE int[0] is the x, int[1] is y, and int[2] is z
}
