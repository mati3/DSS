**Especificación de la operación IHotelMgt::makeReservation()**

**Utilizando los operadores: exists, select y asSequence->first, para especificar la operación IHotelMgt::makeReservation (...) completamente con la notación OCL.**

    context IHotelMgt::makeReservation(in res:ReservationDetails, in cus:CusomertId, out resRef: String): boolean

    	pre:
    	  customer -> exist( c.customer | c.id = cus)
    	  hotel -> exist( h.hotel | h.id = res.hotelID)
    	  roomType -> exist(rt.roomType | rt.name = res.resname)

    	post:
    		Let newReservation = reservation -> select (r | r.resRef = resRef) in
    	    	resRef.allocation -> NotClained = result.clained

    		    Let h = hotel -> select( h.hotel | h.id = res.hotel)-> asSequence -> first in
    		      (h.reservation - h.reservation@pre)-> size = 1 and
    		      Let r =(h.reservation - h.reservation@pre)-> asSequence -> first in
    		        r.resRef = out resRef and
    		        r.claimed = false and
    		        r.dates = res.dates and
    		        r.roomType = res.roomType and
    		        r.customer.id = cus

	    