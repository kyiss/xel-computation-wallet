/*
 * Copyright © 2013-2016 The Nxt Core Developers.
 * Copyright © 2016-2017 Jelurida IP B.V.
 *
 * See the LICENSE.txt file at the top-level directory of this distribution
 * for licensing information.
 *
 * Unless otherwise agreed in a custom licensing agreement with Jelurida B.V.,
 * no part of the Nxt software, including this file, may be copied, modified,
 * propagated, or distributed except according to the terms contained in the
 * LICENSE.txt file.
 *
 * Removal or modification of this copyright notice is prohibited.
 *
 */

package org.xel.http;

import org.xel.Block;
import org.xel.Constants;
import org.xel.Nxt;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;

public final class GetNextBlockGenerators extends APIServlet.APIRequestHandler {

    static final GetNextBlockGenerators instance = new GetNextBlockGenerators();

    private GetNextBlockGenerators() {
        super(new APITag[] {APITag.FORGING});
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) {

        /* implement later, if needed
        Block curBlock;

        String block = req.getParameter("block");
        if (block == null) {
            curBlock = Nxt.getBlockchain().getLastBlock();
        } else {
            try {
                curBlock = Nxt.getBlockchain().getBlock(Convert.parseUnsignedLong(block));
                if (curBlock == null) {
                    return UNKNOWN_BLOCK;
                }
            } catch (RuntimeException e) {
                return INCORRECT_BLOCK;
            }
        }
        */

        Block curBlock = Nxt.getBlockchain().getLastBlock();
        if (curBlock.getHeight() < Constants.TRANSPARENT_FORGING_BLOCK_7) {
            return JSONResponses.FEATURE_NOT_AVAILABLE;
        }


        JSONObject response = new JSONObject();
        response.put("time", Nxt.getEpochTime());
        response.put("lastBlock", Long.toUnsignedString(curBlock.getId()));
        JSONArray hubs = new JSONArray();

        int limit;
        try {
            limit = Integer.parseInt(req.getParameter("limit"));
        } catch (RuntimeException e) {
            limit = Integer.MAX_VALUE;
        }
        return response;
    }

}
