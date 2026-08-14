import type { Meta, StoryObj } from '@storybook/angular';
import { DashboardMarketTable } from './dashboard-market-table.component';

const meta: Meta<DashboardMarketTable> = {
  title: 'Components/DashboardMarketTable',
  component: DashboardMarketTable,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<DashboardMarketTable>;

export const Primary: Story = {
  args: {},
};
