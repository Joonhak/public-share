package org.slam.publicshare.rental.api;

import lombok.RequiredArgsConstructor;
import org.slam.publicshare.account.domain.Account;
import org.slam.publicshare.rental.domain.RentalStatus;
import org.slam.publicshare.rental.dto.RentalResponse;
import org.slam.publicshare.rental.service.RentalFindService;
import org.slam.publicshare.rental.service.RentalSaveService;
import org.slam.publicshare.rental.service.RentalUpdateService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RentalController {

    private final RentalFindService rentalFindService;
    private final RentalSaveService rentalSaveService;
    private final RentalUpdateService rentalUpdateService;

    @GetMapping("/rental")
    public List<RentalResponse> findRentalByAccount(@AuthenticationPrincipal(expression = "account") Account account) {
        return rentalFindService.findByAccountId(account.getId()).stream()
                .map(RentalResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/rental/{bookId}")
    public List<RentalResponse> findRentalByBookId(@PathVariable Long bookId) {
        return rentalFindService.findByBookId(bookId).stream()
                .map(RentalResponse::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/book/{bookId}/rental")
    public RentalResponse rentalRequest(@PathVariable Long bookId, @AuthenticationPrincipal(expression = "account") Account account) {
        return new RentalResponse(rentalSaveService.rentalRequest(bookId, account.getId()));
    }

    @PutMapping("/rental/{rentalId}")
    public RentalResponse changeRentalStatus(@PathVariable Long rentalId, @RequestBody RentalStatus status) {
        return new RentalResponse(rentalUpdateService.updateRental(rentalId, status));
    }

}
