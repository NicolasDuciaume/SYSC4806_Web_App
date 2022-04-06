package SYSC6.Project.user.userManagement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import SYSC6.Project.user.UserRepository;
import SYSC6.Project.user.userManagement.sorting.Column;
import SYSC6.Project.user.userManagement.sorting.Order;
import SYSC6.Project.user.userManagement.sorting.Page;
import SYSC6.Project.user.userManagement.sorting.PagingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Used for generating Page objects of UserPOJO's for use populating dynamic tables
 */
@Slf4j
@Service
public class UserService {

    private static final Comparator<UserPOJO> EMPTY_COMPARATOR = (e1, e2) -> 0;

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserPOJO> getUsers(PagingRequest pagingRequest) {

        List<UserPOJO> users = new ArrayList<>();
        userRepository.findAll().forEach(u -> users.add(u.toPojo()));
        return getPage(users, pagingRequest);

    }

    private Page<UserPOJO> getPage(List<UserPOJO> users, PagingRequest pagingRequest) {
        List<UserPOJO> filtered = users.stream()
                .sorted(sortUsers(pagingRequest))
                .filter(filterUsers(pagingRequest))
                .skip(pagingRequest.getStart())
                .limit(pagingRequest.getLength())
                .collect(Collectors.toList());

        long count = users.stream()
                .filter(filterUsers(pagingRequest))
                .count();

        Page<UserPOJO> page = new Page<>(filtered);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());

        return page;
    }

    private Predicate<UserPOJO> filterUsers(PagingRequest pagingRequest) {
        if (pagingRequest.getSearch() == null || StringUtils.isEmpty(pagingRequest.getSearch()
                .getValue())) {
            return employee -> true;
        }

        String value = pagingRequest.getSearch()
                .getValue();

        return user -> user.getUsername()
                .toLowerCase()
                .contains(value)
                || user.getRole()
                .toLowerCase()
                .contains(value);
    }

    private Comparator<UserPOJO> sortUsers(PagingRequest pagingRequest) {
        if (pagingRequest.getOrder() == null) {
            return EMPTY_COMPARATOR;
        }

        try {
            Order order = pagingRequest.getOrder()
                    .get(0);

            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns()
                    .get(columnIndex);

            Comparator<UserPOJO> comparator = UserComparators.getComparator(column.getData(), order.getDir());
            if (comparator == null) {
                return EMPTY_COMPARATOR;
            }

            return comparator;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return EMPTY_COMPARATOR;
    }
}